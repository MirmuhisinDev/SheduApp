package org.example.shedu.repository;

import org.example.shedu.entity.Notification;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
   Notification findByIdAndUserId(Integer id,Integer userId);
   Page<Notification> findByUserId(Integer userId,
                                   Pageable pageable);
}
