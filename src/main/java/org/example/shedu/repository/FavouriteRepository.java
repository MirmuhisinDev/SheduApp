package org.example.shedu.repository;

import org.example.shedu.entity.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteRepository extends JpaRepository<Favourite, Integer> {
    List<Favourite> findAllByBarbershopIdAndDeletedFalse(Integer barbershopId);
}
