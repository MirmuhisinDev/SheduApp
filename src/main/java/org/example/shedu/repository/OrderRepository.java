package org.example.shedu.repository;

import org.example.shedu.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByBarbershopIdAndDeletedFalse(Integer barbershopId);
    List<Order> findAllByServiceIdAndDeletedFalse(Integer serviceId);
}
