package org.example.shedu.repository;

import jakarta.validation.constraints.Future;
import org.example.shedu.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByActivationCode(Integer activationCode);

    @Query(nativeQuery = true, value = "select * from users where full_name ilike '%' || :fullName || '%'")
    List<User> findByFullName(String fullName);

    Page<User> findAllByDeletedFalse(PageRequest pageable);
    Optional<User> findByIdAndDeletedFalse(Integer id);

}
