package org.example.shedu.repository;

import org.example.shedu.entity.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, Integer> {
    List<Service> findAllByBarbershopIdAndDeletedFalse(Integer barbershopId);
    boolean existsByServiceName(String serviceName);

//    @Query(nativeQuery = true, value = "select s from service s where s.deleted=false")
//    Page<Service> findAllByDeletedFalse(Pageable pageRequest);
    boolean existsByServiceNameAndIdNot(String serviceName, Integer id);

    Service findByIdAndBarbershopIdAndDeletedFalse(Integer serviceId, Integer barbershopId);
}
