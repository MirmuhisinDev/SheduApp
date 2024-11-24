package org.example.shedu.repository;

import org.example.shedu.entity.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    List<Feedback> findAllByBarbershopIdAndDeletedFalse(Integer barbershopId);

    Page<Feedback> findAllByDeletedFalse(PageRequest pageable);
}
