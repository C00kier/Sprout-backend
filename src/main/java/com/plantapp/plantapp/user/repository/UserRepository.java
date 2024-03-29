package com.plantapp.plantapp.user.repository;

import com.plantapp.plantapp.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByResetPasswordToken(String token);
    boolean existsByEmail(String email);
}
