package by.incubator.infrastructure.orm.impl;

import by.incubator.infrastructure.core.annotations.InitMethod;
import by.incubator.infrastructure.core.annotations.Property;
import by.incubator.infrastructure.orm.ConnectionFactory;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactoryImpl implements ConnectionFactory {
    @Property
    private String url;
    @Property
    private String username;
    @Property
    private String password;
    private Connection connection;

    public ConnectionFactoryImpl() {
    }

    @SneakyThrows
    @InitMethod
    public void initConnection(){
        Class.forName("org.postgresql.Driver");
        this.connection = DriverManager.getConnection(url, username, password);
    }

    @Override
    public Connection getConnection() {
        return this.connection;
    }
}
