package com.example.demo.Repository;

import com.example.demo.Model.Entity.UsersLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersLoginRepository extends JpaRepository<UsersLogin, Long> {
    Optional<UsersLogin> findByUsername(String username);
}
