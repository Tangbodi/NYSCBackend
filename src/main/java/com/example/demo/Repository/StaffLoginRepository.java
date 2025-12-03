package com.example.demo.Repository;

import com.example.demo.Model.Entity.StaffsLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffLoginRepository extends JpaRepository<StaffsLogin, Long> {
    Optional<StaffsLogin> findByUsername(String username);
}
