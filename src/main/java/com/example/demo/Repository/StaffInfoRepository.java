package com.example.demo.Repository;

import com.example.demo.Model.Entity.StaffInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffInfoRepository extends JpaRepository<StaffInfo, Long> {
    Optional<StaffInfo> findByUsername(String username);

    Optional<StaffInfo> findByEmail(String email);
}
