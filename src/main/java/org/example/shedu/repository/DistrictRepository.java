package org.example.shedu.repository;

import org.example.shedu.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistrictRepository extends JpaRepository<District, Integer> {
    boolean existsByNameAndDeletedIsFalse(String name);
}
