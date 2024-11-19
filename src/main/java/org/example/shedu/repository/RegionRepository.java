package org.example.shedu.repository;

import org.example.shedu.entity.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Integer> {
    Optional<Region> findByName(String name);
    boolean existsByNameAndDeletedIsFalse(String name);

    Page<Region> findAllByDeletedIsFalse(PageRequest pageRequest);

    boolean existsByNameAndIdNot(String name, Integer id);
}
