package org.example.shedu.repository;

import org.example.shedu.entity.Order;
import org.example.shedu.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findAllByServiceIdAndDeletedFalse(Integer serviceId);
}
