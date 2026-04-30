package com.example.demo.Model.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "program_targets")
public class ProgramTarget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "target_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "program_id", nullable = false)
    private Long programId;

    @Size(max = 63)
    @Column(name = "target_name", length = 63)
    private String targetName;

    @Size(max = 63)
    @Column(name = "objective", length = 63)
    private String objective;

    @Size(max = 31)
    @Column(name = "status", length = 31)
    private String status;

    @Size(max = 31)
    @Column(name = "date_opened", length = 31)
    private String dateOpened;

    @Size(max = 31)
    @Column(name = "date_mastered", length = 31)
    private String dateMastered;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;
}
