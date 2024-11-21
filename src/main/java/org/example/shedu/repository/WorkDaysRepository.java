package org.example.shedu.repository;

import org.example.shedu.entity.WorkDays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface WorkDaysRepository extends JpaRepository<WorkDays, Integer> {

    Optional<WorkDays> findByBarbershopIdAndDeletedFalse(Integer id);
}
