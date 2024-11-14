package org.example.shedu.repository;

import org.example.shedu.entity.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region, Integer> {
    boolean existsByNameAndDeletedIsFalse(String name);

    Page<Region> findAllByDeletedIsFalse(Pageable pageable);

    boolean existsByNameAndIdNot(String name, Integer id);
}
