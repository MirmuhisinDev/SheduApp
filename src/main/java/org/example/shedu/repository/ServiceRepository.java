package org.example.shedu.repository;

import org.example.shedu.entity.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, Integer> {
    List<Service> findAllByBarbershopIdAndDeletedFalse(Integer barbershopId);
    Optional<Service> findByServiceName(String serviceName);
    Page<Service> findAllByDeletedFalse(Pageable pageable);
    boolean existsByServiceNameAndIdNot(String serviceName, Integer id);
}
