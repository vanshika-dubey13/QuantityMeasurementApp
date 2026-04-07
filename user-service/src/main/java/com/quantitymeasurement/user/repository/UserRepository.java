package com.quantitymeasurement.user.repository;

import com.quantitymeasurement.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Spring generates: SELECT * FROM users WHERE username = ?
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}