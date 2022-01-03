package by.incubator.infrastructure.orm.service;

import by.incubator.Entity.Orders;
import by.incubator.Entity.Rents;
import by.incubator.Entity.Types;
import by.incubator.Entity.Vehicles;
import by.incubator.infrastructure.core.Context;
import by.incubator.infrastructure.core.annotations.Autowired;
import by.incubator.infrastructure.core.annotations.InitMethod;
import by.incubator.infrastructure.orm.ConnectionFactory;
import by.incubator.infrastructure.config.annotations.Column;
import by.incubator.infrastructure.config.annotations.ID;
import by.incubator.infrastructure.config.annotations.Table;
import by.incubator.infrastructure.orm.enums.SqlFieldType;
import javassist.NotFoundException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;

public class PostgreDataBaseService {
    @Autowired
    private Context context;
    @Autowired
    private ConnectionFactory connectionFactory;
    private Map<String,String> classToSql;
    private Map<String,String> insertPatternByClass;
    private Map<String, String> insertByClassPattern;

    private static final String SEQ_NAME = "id_seq";
    private static final String CHECK_SEQ_SQL_PATTERN = "SELECT EXISTS (\n" +
            "    SELECT FROM information_schema.sequences \n" +
            "    WHERE sequence_schema = 'public' \n" +
            "    AND   sequence_name   = '%s'\n"+
            ");";
    private static final String  CHECK_ID_SQL_PATTERN = "CREATE SEQUENCE %S\n" +
            "    INCREMENT 1\n" +
            "    START 1;";
    private static final String CHECK_TABLE_SQL_PATTERN = "SELECT EXISTS (\n" +
            "    SELECT FROM information_schema.tables \n" +
            "    WHERE table_schema = 'public' \n" +
            "    AND   table_name   = '%s'\n"+
            ");";
    private static final String CREATE_TABLE_SQL_PATTERN = "CREATE TABLE %s (\n" +
            "    %s integer PRIMARY KEY DEFAULT nextval('%s'),\n" +
            "    %S\n);";
    private static final String INSERT_SQL_PATTERN = "INSERT INTO %s(%s)\n" +
            "    VALUES (%s)\n" +
            "    RETURNING %s;";

    public PostgreDataBaseService() {
    }
    @InitMethod
    public void init() throws Exception {
        Connection connection = connectionFactory.getConnection();
//1
        initClassToSql();
//2
        initInsertPatternByClass();
//3
        boolean isExists = checkIsExistSequenceWithName();
//4
        createSequenceIfNotExists(isExists);
//5
        Set<Class<?>> entities = context.getConfig().getScanner().getReflections().getTypesAnnotatedWith(Table.class);
//6
        for(Class<?> en : entities)
            if(!checkHaveIdAndOtherColumn(en))
                throw new Exception("Table hasn't column ID or has fields without annotation");
//7,8
        checkExistEntityTable(entities);
//9
        initInsertByClassPattern(entities);
    }

    private void initInsertByClassPattern(Set<Class<?>> entities) {
        insertByClassPattern = new HashMap<>();
        for(Class<?> entity : entities) {
            String tableName = entity.getAnnotation(Table.class).name();

            StringBuilder columnNames = new StringBuilder();
            List<Field> fieldsColumns = new ArrayList<>(List.of(entity.getDeclaredFields()));
            fieldsColumns.removeIf(f -> !f.isAnnotationPresent(Column.class));
            for (Field f : fieldsColumns)
                columnNames.append(",").append(f.getAnnotation(Column.class).name());
            columnNames.deleteCharAt(0);
            StringBuilder valuesPattern = new StringBuilder();

            for (Field f : fieldsColumns) {
                String getType = f.getType().getName();
                String getInsertPat = insertPatternByClass.get(getType);
                valuesPattern.append(",").append(getInsertPat);
            }
            valuesPattern.deleteCharAt(0);

            String idColumn = Arrays.stream(entity.getDeclaredFields()).filter(x -> x.isAnnotationPresent(ID.class)).findFirst().get().getName();

            String value = String.format(INSERT_SQL_PATTERN, tableName, columnNames, valuesPattern, idColumn);
            insertByClassPattern.put(entity.getSimpleName(), value);
        }
    }

    private void checkExistEntityTable(Set<Class<?>> entities) {
        for(Class<?> entity : entities){
            boolean isTableExist = false;
            String tableName = entity.getAnnotation(Table.class).name();//??????????????
            String sql = String.format(CHECK_TABLE_SQL_PATTERN,tableName);
            try (Statement statement = connectionFactory.getConnection().createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)){
                resultSet.next();
                isTableExist = resultSet.getBoolean("exists");
            } catch (SQLException e) {
                e.printStackTrace();
            }

            createTableIfNotExist(entity, isTableExist, tableName);
        }
    }

    private void createTableIfNotExist(Class<?> entity, boolean isTableExist, String tableName) {
        if(!isTableExist){
            String idField = Arrays.stream(entity.getDeclaredFields()).filter(x -> x.isAnnotationPresent(ID.class)).findFirst().get().getName();
            Field[] allFields = entity.getDeclaredFields();
            StringBuilder fields = new StringBuilder();
            for(Field f : allFields){
                if (f.isAnnotationPresent(Column.class)) {
                    fields.append(" ").append(f.getAnnotation(Column.class).name())
                            .append(" ").append(classToSql.get(f.getType().getSimpleName()));
                    if (f.getAnnotation(Column.class).nullable())
                        fields.append(" NOT NULL");
                    if (f.getAnnotation(Column.class).unique())
                        fields.append(" UNIQUE");
                    fields.append(',');
                }
            }
            fields.deleteCharAt(fields.lastIndexOf(","));

            String sql = String.format(CREATE_TABLE_SQL_PATTERN, tableName, idField, SEQ_NAME, fields);
            try (Statement statement = connectionFactory.getConnection().createStatement()){
                statement.executeQuery(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void createSequenceIfNotExists(boolean isExists) {
        if(!isExists){
            try (Statement statement = connectionFactory.getConnection().createStatement()){
                String sql = String.format(CHECK_ID_SQL_PATTERN,SEQ_NAME);
                statement.executeQuery(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean checkIsExistSequenceWithName() {
        boolean isExists = false;
        String sql = String.format(CHECK_SEQ_SQL_PATTERN,SEQ_NAME);
        //sql = "select * from table;";
        try (Statement statement = connectionFactory.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sql)){
             resultSet.next();
            isExists = resultSet.getBoolean("exists");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isExists;
    }

    private void initInsertPatternByClass() {
        insertPatternByClass = new HashMap<>();
        for(SqlFieldType s : SqlFieldType.values())
            insertPatternByClass.put(s.getType().getName(),s.getInsertPattern());
    }

    private void initClassToSql() {
        classToSql = new HashMap<>();
        for(SqlFieldType s : SqlFieldType.values()) {
            classToSql.put(s.getType().getSimpleName(),s.getSqlType());
        }

    }

    private boolean checkHaveIdAndOtherColumn(Class<?> en) {
        List<Field> fields = List.of(en.getDeclaredFields());
        boolean hasId = false, hasNotColumn = false;
        for (Field f : fields) {
            if(f.isAnnotationPresent(ID.class) && f.getType() == Long.class)
                hasId = true;
            if(!f.isAnnotationPresent(ID.class) || !f.isAnnotationPresent(Column.class))
                hasNotColumn = true;
            if(hasId && hasNotColumn)
                return true;
        }
        return false;
    }


    public <T> Optional<T> get(Long id, Class<T> clazz) {
        Optional<T> obj = Optional.empty();
        if (clazz.isAnnotationPresent(Table.class)){
            String sql = "SELECT * FROM " + clazz.getAnnotation(Table.class).name() + " WHERE " + clazz.getAnnotation(ID.class).name() + " = " + id;
            try (Statement statement = connectionFactory.getConnection().createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)){
                resultSet.next();
                obj = (Optional<T>) makeObject(resultSet, clazz);
            } catch (SQLException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        else
            try {
                throw new Exception("Class not declared as Table annotation");
            } catch (Exception e) {
                e.printStackTrace();
            }
        return obj;
    }

    public Long save(Object obj) {
        Long id = 0L;
        Object[] values = getFieldsWithAnnotationColumn(obj);
        String sql = String.format(insertByClassPattern.get(obj.getClass().getName()), values);
        try (Statement statement = connectionFactory.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(sql)){
            String idFieldName = "";
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field f : fields)
                if(f.isAnnotationPresent(ID.class)) {
                    idFieldName = f.getName();
                    break;
                }

            resultSet.next();
            id = resultSet.getLong(idFieldName);
            Field idField = obj.getClass().getDeclaredField(idFieldName);
            idField.setAccessible(true);
            idField.set(obj, id);
        } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return id;
    }

    private Object[] getFieldsWithAnnotationColumn(Object obj) {
        List<Field> fields = new ArrayList<>(List.of(obj.getClass().getDeclaredFields()));
        fields.removeIf(f -> !f.isAnnotationPresent(Column.class));
        Object[] values = new Object[fields.size()];

        int i = 0;
        for(Field f : fields) {
            Class clazz = obj.getClass();
            Method getterByFieldName = null;
            try {
                getterByFieldName = clazz.getMethod("get" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1), f.getType());
                values[i] = getterByFieldName.invoke(obj);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            i++;
        }

        return values;
    }

    public <T> List<T> getAll(Class<T> clazz) {
        List<T> result = null;
        if(clazz.isAnnotationPresent(Table.class)){
            String sql = "SELECT * FROM " + clazz.getAnnotation(Table.class).name();
            try (Statement statement = connectionFactory.getConnection().createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)){
                result = new ArrayList<>();
                while (resultSet.next()) {
                    T obj = makeObject(resultSet,clazz);
                    result.add(obj);
                }
            } catch (SQLException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                throw new NotFoundException("Table doesn't exist");
            } catch (NotFoundException e) {
                e.getMessage();
            }
        }
        return result;
    }

    private <T> T makeObject(ResultSet resultSet, Class<T> clazz) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Field[] fields = clazz.getDeclaredFields();

        T object = clazz.getDeclaredConstructor().newInstance();

        for(Field field: fields) {
            if(!field.isAnnotationPresent(ID.class)) {
                field.setAccessible(true);
                Column column = field.getAnnotation(Column.class);
                Class<?> type = field.getType();
                Object value = null;
                try {
                    if(type.equals(Long.class))
                        value = resultSet.getLong(column.name());
                    else value = resultSet.getObject(column.name());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                field.set(object, value);
            } else {
                try {
                    field.setAccessible(true);
                    Long l = resultSet.getLong(field.getName());
                    field.set(object, l);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }

        return object;
    }
}
