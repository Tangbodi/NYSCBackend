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
@Table(name = "event_audit_trail")
public class EventAuditTrail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "audit_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Size(max = 63)
    @NotNull
    @Column(name = "field_name", nullable = false, length = 63)
    private String fieldName;

    @Size(max = 511)
    @Column(name = "old_value", length = 511)
    private String oldValue;

    @Size(max = 511)
    @Column(name = "new_value", length = 511)
    private String newValue;

    @Size(max = 63)
    @NotNull
    @Column(name = "modified_by", nullable = false, length = 63)
    private String modifiedBy;

    @NotNull
    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;
}
