package org.example.shedu.repository;

import org.example.shedu.entity.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FavouriteRepository extends JpaRepository<Favourite, Integer> {
    List<Favourite> findAllByBarbershopIdAndDeletedFalse(Integer barbershopId);

    List<Favourite> findAllByUserIdAndDeletedFalse(Integer id);

}
