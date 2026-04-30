package com.example.demo.Repository;

import com.example.demo.Model.Entity.SupervisorSupport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupervisorSupportRepository extends JpaRepository<SupervisorSupport, Integer> {
}
