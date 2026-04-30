package com.example.demo.Repository;

import com.example.demo.Model.Entity.SkillStrategies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillStrategiesRepository extends JpaRepository<SkillStrategies, Integer> {
}
