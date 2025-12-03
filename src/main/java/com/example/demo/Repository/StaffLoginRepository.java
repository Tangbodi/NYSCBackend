package com.example.demo.Repository;

import com.example.demo.Model.Entity.StaffLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffLoginRepository extends JpaRepository<StaffLogin, Long> {
    Optional<StaffLogin> findByUsername(String username);
}
