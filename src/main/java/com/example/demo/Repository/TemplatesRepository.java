package com.example.demo.Repository;

import com.example.demo.Model.Entity.Templates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplatesRepository extends JpaRepository<Templates, Integer> {
}
