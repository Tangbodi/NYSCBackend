package com.example.demo.Repository;

import com.example.demo.Model.Entity.BehaviorStrategies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BehaviorStrategiesRepository extends JpaRepository<BehaviorStrategies, Integer> {
}
