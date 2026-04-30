package com.example.demo.Model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "session_notes")
public class SessionNotes {

    @Id
    @Column(name = "session_id", nullable = false)
    private Long id;

    @Size(max = 63)
    @NotNull
    @Column(name = "template", nullable = false, length = 63)
    private String template;

    @Size(max = 255)
    @NotNull
    @Column(name = "purpose_of_session", nullable = false, length = 255)
    private String purposeOfSession;

    @Size(max = 4095)
    @NotNull
    @Column(name = "client_status", nullable = false, length = 4095)
    private String clientStatus;

    @Size(max = 255)
    @NotNull
    @Column(name = "skill_strategies", nullable = false, length = 255)
    private String skillStrategies;

    @Size(max = 255)
    @NotNull
    @Column(name = "behavior_strategies", nullable = false, length = 255)
    private String behaviorStrategies;

    @Size(max = 63)
    @NotNull
    @Column(name = "supervisor_support", nullable = false, length = 63)
    private String supervisorSupport;

    @Size(max = 4095)
    @NotNull
    @Column(name = "client_response", nullable = false, length = 4095)
    private String clientResponse;

    @Size(max = 4095)
    @NotNull
    @Column(name = "summary_of_progress", nullable = false, length = 4095)
    private String summaryOfProgress;

    @Size(max = 63)
    @NotNull
    @Column(name = "last_modified_by", nullable = false, length = 63)
    private String lastModifiedBy;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;
}
