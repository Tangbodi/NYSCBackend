package com.example.demo.Model.Entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "staffs_info")
public class StaffsInfo {
    @Id
    @Column(name = "staff_id", nullable = false)
    private Long id;

    @Size(max = 31)
    @NotNull
    @Column(name = "username", nullable = false, length = 31)
    private String username;

    @Size(max = 63)
    @NotNull
    @Column(name = "email", nullable = false, length = 63)
    private String email;

    @Size(max = 31)
    @NotNull
    @Column(name = "first_name", nullable = false, length = 31)
    private String firstName;

    @Size(max = 31)
    @NotNull
    @Column(name = "last_name", nullable = false, length = 31)
    private String lastName;

    @Size(max = 11)
    @Column(name = "phone", length = 11)
    private String phone;

    @Size(max = 7)
    @NotNull
    @Column(name = "title", nullable = false, length = 7)
    private String title;

    @NotNull
    @Column(name = "status", nullable = false)
    private Boolean status = false;

    @Size(max = 31)
    @NotNull
    @Column(name = "middle_name", nullable = false, length = 31)
    private String middleName;

    @Size(max = 15)
    @NotNull
    @Column(name = "employee_type", nullable = false, length = 15)
    private String employeeType;

    @Size(max = 63)
    @NotNull
    @Column(name = "supervisor", nullable = false, length = 63)
    private String supervisor;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;

}