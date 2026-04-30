package com.example.demo.Repository;

import com.example.demo.Model.Entity.EventDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventDetailsRepository extends JpaRepository<EventDetails, Long> {

    List<EventDetails> findByClientId(Long clientId);
}
