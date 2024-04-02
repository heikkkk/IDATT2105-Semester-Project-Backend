package no.ntnu.idi.idatt2105.quizopia.backend.model;

import java.time.LocalDateTime;

public class Template {

    private Long templateId;
    private String name;
    private String description;
    private String filepath;
    private LocalDateTime createdAt;
    private Long userId; // Foreign key reference to Users

    // Constructors
    public Template() {
    }

    public Template(Long templateId, String name, String description, String filepath, LocalDateTime createdAt, Long userId) {
        this.templateId = templateId;
        this.name = name;
        this.description = description;
        this.filepath = filepath;
        this.createdAt = createdAt;
        this.userId = userId;
    }

    // Getters and Setters
    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Template{" +
                "templateId=" + templateId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", filepath='" + filepath + '\'' +
                ", createdAt=" + createdAt +
                ", userId=" + userId +
                '}';
    }
}
