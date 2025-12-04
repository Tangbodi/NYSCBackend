package com.example.demo.Repository;

import com.example.demo.Model.Entity.StaffsLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffsLoginRepository extends JpaRepository<StaffsLogin, Long> {
    Optional<StaffsLogin> findByUsername(String username);
}
