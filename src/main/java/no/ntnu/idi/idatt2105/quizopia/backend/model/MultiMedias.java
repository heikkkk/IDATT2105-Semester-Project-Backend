package no.ntnu.idi.idatt2105.quizopia.backend.model;

import java.time.LocalDateTime;

public class MultiMedias {

    private Long media_id;
    private String filePath;
    private String description;
    private LocalDateTime createdAt;
    private Long type_id; // Foreign key reference to MediaTypes

    // Constructors
    public MultiMedias() {
    }

    public MultiMedias(Long media_id, String filePath, String description, LocalDateTime createdAt, Long type_id) {
        this.media_id = media_id;
        this.filePath = filePath;
        this.description = description;
        this.createdAt = createdAt;
        this.type_id = type_id;
    }

    // Getters and Setters
    public Long getMediaId() {
        return media_id;
    }

    public void setMediaId(Long media_id) {
        this.media_id = media_id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getTypeId() {
        return type_id;
    }

    public void setTypeId(Long type_id) {
        this.type_id = type_id;
    }

    @Override
    public String toString() {
        return "MultiMedias{" +
                "media_id=" + media_id +
                ", filePath='" + filePath + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", type_id=" + type_id +
                '}';
    }
}

