package org.example.shedu.repository;

import org.example.shedu.entity.Barbershop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BarbershopRepository extends JpaRepository<Barbershop, Integer> {
    Optional<Barbershop> findByName(String name);
    Page<Barbershop> findAllByDeletedFalse(Pageable pageable);
    List<Barbershop> findAllByDistrictIdAndDeletedFalse(Integer districtId);
    boolean existsByNameAndIdNot(String name, Integer id);
}
