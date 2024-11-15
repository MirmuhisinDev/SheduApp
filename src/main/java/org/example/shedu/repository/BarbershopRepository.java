package org.example.shedu.repository;

import org.example.shedu.entity.Barbershop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BarbershopRepository extends JpaRepository<Barbershop, Integer> {
    List<Barbershop> findAllByDistrictIdAndDeletedFalse(Integer districtId);
}
