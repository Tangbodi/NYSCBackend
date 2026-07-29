package com.example.demo.Repository;

import com.example.demo.Model.Entity.SessionNotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface SessionNotesRepository extends JpaRepository<SessionNotes, Long> {

    @Modifying
    @Query(value = "UPDATE session_notes SET " +
            "template = :template, " +
            "purpose_of_session = :purposeOfSession, " +
            "client_status = :clientStatus, " +
            "skill_strategies = :skillStrategies, " +
            "behavior_strategies = :behaviorStrategies, " +
            "supervisor_support = :supervisorSupport, " +
            "client_response = :clientResponse, " +
            "summary_of_progress = :summaryOfProgress, " +
            "last_modified_by = :lastModifiedBy, " +
            "modified_at = :modifiedAt " +
            "WHERE session_id = :sessionId",
            nativeQuery = true)
    void UpdateSessionNotes(
            @Param("sessionId") Long sessionId,
            @Param("template") String template,
            @Param("purposeOfSession") String purposeOfSession,
            @Param("clientStatus") String clientStatus,
            @Param("skillStrategies") String skillStrategies,
            @Param("behaviorStrategies") String behaviorStrategies,
            @Param("supervisorSupport") String supervisorSupport,
            @Param("clientResponse") String clientResponse,
            @Param("summaryOfProgress") String summaryOfProgress,
            @Param("lastModifiedBy") String lastModifiedBy,
            @Param("modifiedAt") Instant modifiedAt
    );
}
