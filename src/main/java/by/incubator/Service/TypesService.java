package by.incubator.Service;

import by.incubator.Entity.Types;
import by.incubator.infrastructure.core.annotations.Autowired;
import by.incubator.infrastructure.core.annotations.InitMethod;
import by.incubator.infrastructure.orm.EntityManager;

import java.util.List;

public class TypesService {
    @Autowired
    EntityManager entityManager;
    @InitMethod
    public void init(){}

    public Types get(Long id){
        if(entityManager.get(id, Types.class).isPresent())
            return entityManager.get(id, Types.class).get();
        return null;
    }

    public List<Types> getAll(){
        return entityManager.getAll(Types.class);
    }

    public Long save(Types type){
        return entityManager.save(type);
    }
}
