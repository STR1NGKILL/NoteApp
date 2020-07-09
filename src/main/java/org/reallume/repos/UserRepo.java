package org.reallume.repos;

import org.reallume.domain.Category;
import org.reallume.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByToken(String token);

    void deleteById(Long user_id);

    List<User> findAll();

}