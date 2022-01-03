package by.incubator.Service;

import by.incubator.Entity.Rents;
import by.incubator.infrastructure.core.annotations.Autowired;
import by.incubator.infrastructure.core.annotations.InitMethod;
import by.incubator.infrastructure.orm.EntityManager;

import java.util.List;

public class RentsService {
    @Autowired
    EntityManager entityManager;
    @InitMethod
    public void init(){}

    public Rents get(Long id){
        if(entityManager.get(id, Rents.class).isPresent())
            return entityManager.get(id, Rents.class).get();
        return null;
    }

    public List<Rents> getAll(){
        return entityManager.getAll(Rents.class);
    }

    public Long save(Rents rent){
        return entityManager.save(rent);
    }
}
