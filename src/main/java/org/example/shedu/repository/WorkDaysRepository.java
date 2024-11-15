package org.example.shedu.repository;

import org.example.shedu.entity.WorkDays;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkDaysRepository extends JpaRepository<WorkDays, Integer> {
    List<WorkDays> findAllByBarbershopIdAndDeletedFalse(Integer questionId);
}
