package com.example.demo.Repository;

import com.example.demo.Model.Entity.PurposeOfSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurposeOfSessionRepository extends JpaRepository<PurposeOfSession, Integer> {
}
