package com.example.docapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "title is required")
    @Size(max = 200, message = "title must be 200 characters or fewer")
    private String title;

    @NotBlank(message = "fileType is required")
    private String fileType;

    @Size(max = 1000, message = "description must be 1000 characters or fewer")
    private String description;

    @NotNull(message = "status is required")
    @Enumerated(EnumType.STRING)
    private DocumentStatus status;

    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Document() {}

    public Document(String title, String fileType, String description, DocumentStatus status) {
        this.title = title;
        this.fileType = fileType;
        this.description = description;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public DocumentStatus getStatus() { return status; }
    public void setStatus(DocumentStatus status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}