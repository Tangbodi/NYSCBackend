package com.example.demo.Repository;

import com.example.demo.Model.Entity.StaffsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffsInfoRepository extends JpaRepository<StaffsInfo, Long> {
    Optional<StaffsInfo> findByUsername(String username);

    Optional<StaffsInfo> findByEmail(String email);
}
