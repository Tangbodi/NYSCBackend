package com.example.demo.Repository;

import com.example.demo.Model.Entity.EventAuditTrail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventAuditTrailRepository extends JpaRepository<EventAuditTrail, Long> {

    List<EventAuditTrail> findByEventIdOrderByModifiedAtDesc(Long eventId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM event_audit_trail WHERE event_id IN (:eventIds)", nativeQuery = true)
    void deleteByEventIdIn(@Param("eventIds") List<Long> eventIds);
}
