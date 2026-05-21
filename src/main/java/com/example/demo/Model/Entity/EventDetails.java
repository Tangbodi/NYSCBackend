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
@Table(name = "event_details")
public class EventDetails {

    @Id
    @Column(name = "event_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @NotNull
    @Column(name = "staff_id", nullable = false)
    private Long staffId;

    @Size(max = 15)
    @NotNull
    @Column(name = "type", nullable = false, length = 15)
    private String type;

    @Size(max = 15)
    @NotNull
    @Column(name = "date", nullable = false, length = 15)
    private String date;

    @Size(max = 15)
    @NotNull
    @Column(name = "start_time", nullable = false, length = 15)
    private String startTime;

    @Size(max = 15)
    @NotNull
    @Column(name = "end_time", nullable = false, length = 15)
    private String endTime;

    @Size(max = 15)
    @NotNull
    @Column(name = "pay_code", nullable = false, length = 15)
    private String payCode;

    @Size(max = 63)
    @NotNull
    @Column(name = "client_name", nullable = false, length = 63)
    private String clientName;

    @Size(max = 63)
    @NotNull
    @Column(name = "staff_member", nullable = false, length = 63)
    private String staffMember;

    @Size(max = 127)
    @NotNull
    @Column(name = "service", nullable = false, length = 127)
    private String service;

    @Size(max = 45)
    @NotNull
    @Column(name = "client_contact_reminders", nullable = false, length = 45)
    private String clientContactReminders;

    @Size(max = 63)
    @NotNull
    @Column(name = "place_of_service", nullable = false, length = 63)
    private String placeOfService;

    @Size(max = 63)
    @NotNull
    @Column(name = "last_modified_by", nullable = false, length = 63)
    private String lastModifiedBy;

    @Size(max = 15)
    @Column(name = "tag", length = 15)
    private String tag;

    @Size(max = 63)
    @Column(name = "staff_reminders", length = 63)
    private String staffReminders;

    @Size(max = 15)
    @Column(name = "verifications", length = 15)
    private String verifications;

    @Size(max = 15)
    @Column(name = "cancellations", length = 15)
    private String cancellations;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;
}
