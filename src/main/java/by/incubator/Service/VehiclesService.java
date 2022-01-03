package by.incubator.Service;

import by.incubator.Entity.Vehicles;
import by.incubator.infrastructure.core.annotations.Autowired;
import by.incubator.infrastructure.core.annotations.InitMethod;
import by.incubator.infrastructure.orm.EntityManager;

import java.util.List;

public class VehiclesService {
    @Autowired
    EntityManager entityManager;
    @InitMethod
    public void init(){}

    public Vehicles get(Long id){
        if(entityManager.get(id, Vehicles.class).isPresent())
            return entityManager.get(id, Vehicles.class).get();
        return null;
    }

    public List<Vehicles> getAll(){
        return entityManager.getAll(Vehicles.class);
    }

    public Long save(Vehicles vehicle){
        return entityManager.save(vehicle);
    }
}
