package no.ntnu.idi.idatt2105.quizopia.backend.model;

import java.time.LocalDateTime;

public class MultiMedia {

    private Long mediaId;
    private String filePath;
    private String description;
    private LocalDateTime createdAt;
    private Long typeId; // Foreign key reference to MediaType

    // Constructors
    public MultiMedia() {
    }

    public MultiMedia(Long mediaId, String filePath, String description, LocalDateTime createdAt, Long typeId) {
        this.mediaId = mediaId;
        this.filePath = filePath;
        this.description = description;
        this.createdAt = createdAt;
        this.typeId = typeId;
    }

    // Getters and Setters
    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
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
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "MultiMedia{" +
                "mediaId=" + mediaId +
                ", filePath='" + filePath + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", typeId=" + typeId +
                '}';
    }
}

