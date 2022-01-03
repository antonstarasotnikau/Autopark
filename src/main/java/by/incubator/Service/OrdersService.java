package by.incubator.Service;

import by.incubator.Entity.Orders;
import by.incubator.infrastructure.core.annotations.Autowired;
import by.incubator.infrastructure.core.annotations.InitMethod;
import by.incubator.infrastructure.orm.EntityManager;

import java.util.List;

public class OrdersService {
    @Autowired
    EntityManager entityManager;
    @InitMethod
    public void init(){}

    public Orders get(Long id){
        if(entityManager.get(id, Orders.class).isPresent())
            return entityManager.get(id, Orders.class).get();
        return null;
    }

    public List<Orders> getAll(){
        return entityManager.getAll(Orders.class);
    }

    public Long save(Orders order){
        return entityManager.save(order);
    }
}
