package org.example.shedu.repository;

import org.example.shedu.entity.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, Integer> {
    List<Service> findAllByBarbershopIdAndDeletedFalse(Integer barbershopId);
    Optional<Service> findByServiceName(String serviceName);
    Page<Service> findAllByDeletedFalse(PageRequest pageRequest);
    boolean existsByServiceNameAndIdNot(String serviceName, Integer id);

    @Query(value = "select s from Service s where s.id = ?1 and s.barbershop=?2 and s.deleted=false ")
    Service findById(Integer id, Integer barbershopId);
}
