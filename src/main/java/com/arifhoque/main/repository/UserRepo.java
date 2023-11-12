package com.arifhoque.main.repository;

import com.arifhoque.main.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsernameIgnoreCase(String username);
}
