package by.incubator.infrastructure.orm.impl;

import by.incubator.infrastructure.core.Context;
import by.incubator.infrastructure.core.annotations.Autowired;
import by.incubator.infrastructure.orm.ConnectionFactory;
import by.incubator.infrastructure.orm.EntityManager;
import by.incubator.infrastructure.orm.service.PostgreDataBaseService;

import java.util.List;
import java.util.Optional;

public class EntityManagerImpl implements EntityManager {
    @Autowired
    private ConnectionFactory connection;
    @Autowired
    private PostgreDataBaseService dataBaseService;
    @Autowired
    private Context context;

    public EntityManagerImpl() {
    }

    @Override
    public <T> Optional<T> get(Long id, Class<T> clazz) {
        return dataBaseService.get(id, clazz);
    }

    @Override
    public Long save(Object object) {
        return dataBaseService.save(object);
    }

    @Override
    public <T> List<T> getAll(Class<T> clazz) {
        return dataBaseService.getAll(clazz);
    }
}
