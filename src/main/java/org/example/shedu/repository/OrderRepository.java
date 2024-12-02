package org.example.shedu.repository;

import org.example.shedu.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByBarbershopIdAndDeletedFalse(Integer barbershopId);
    List<Order> findAllByServiceIdAndDeletedFalse(Integer serviceId);
    Optional<Order> findByIdAndDeletedFalse(Integer id);

    List<Order> findByCreateAtBetweenAndSentNotificationFalse(LocalDateTime start, LocalDateTime end);
    List<Order> findAllByUserIdAndDeletedFalse(Integer userId);

    @Query("SELECT o FROM Order o WHERE o.orderDay = :orderDay AND " +
            "((o.startTime <= :endTime AND o.endTime >= :startTime))")
    Optional<Order> findByOrderDayAndTimeOverlap(LocalDate orderDay, LocalTime startTime, LocalTime endTime);





}
