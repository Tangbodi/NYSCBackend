package com.example.demo.Model.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "file_cabinet")
public class FileCabinet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Size(max = 255)
    @NotNull
    @Column(name = "file_name", nullable = false, length = 255)
    private String fileName;

    @Size(max = 63)
    @Column(name = "tag", length = 63)
    private String tag;

    @Column(name = "file_type", length = 31)
    private String fileType;

    @Size(max = 512)
    @NotNull
    @Column(name = "file_path", nullable = false, length = 512)
    private String filePath;

    @Size(max = 512)
    @NotNull
    @Column(name = "file_url", nullable = false, length = 512)
    private String fileUrl;

    @NotNull
    @Column(name = "added_by", nullable = false)
    private Long addedBy;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @Column(name = "modified_at", nullable = false)
    private Instant modifiedAt;
}
