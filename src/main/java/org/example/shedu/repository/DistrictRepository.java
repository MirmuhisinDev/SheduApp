package org.example.shedu.repository;

import org.example.shedu.entity.District;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistrictRepository extends JpaRepository<District, Integer> {

    boolean existsByNameAndDeletedIsFalse(String name);

    Page<District> findAllByDeletedFalse(Pageable pageable);

    boolean existsByNameAndIdNot(String districtName, Integer id);

    List<District> findAllByRegionIdAndDeletedFalse(Integer regionId);
    List<District> findAllByIdAndDeletedFalse(Integer questionId);
}
