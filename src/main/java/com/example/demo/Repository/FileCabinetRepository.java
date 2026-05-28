package com.example.demo.Repository;

import com.example.demo.Model.Entity.FileCabinet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileCabinetRepository extends JpaRepository<FileCabinet, Integer> {

    List<FileCabinet> findByClientIdOrderByCreatedAtDesc(Long clientId);
}
