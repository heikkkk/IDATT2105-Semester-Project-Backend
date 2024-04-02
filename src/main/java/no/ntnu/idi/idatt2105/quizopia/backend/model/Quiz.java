package no.ntnu.idi.idatt2105.quizopia.backend.model;

import java.time.LocalDateTime;

public class Quiz {

    private Long quizId;
    private String title;
    private String description;
    private boolean isPublic;
    private LocalDateTime createdAt;
    private Long templateId; // Foreign key reference to Template
    private Long categoryId; // Foreign key reference to Category 
    private Long mediaId;    // Foreign key reference to Multi_Medias;

    // Constructors
    public Quiz() {
    }

    public Quiz(Long quizId, String title, String description, boolean isPublic,
                LocalDateTime createdAt, Long templateId, Long categoryId, Long mediaId) {
        this.quizId = quizId;
        this.title = title;
        this.description = description;
        this.isPublic = isPublic;
        this.createdAt = createdAt;
        this.templateId = templateId;
        this.categoryId = categoryId;
        this.mediaId = mediaId;
    }

    // Getters and Setters
    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "quizId=" + quizId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isPublic=" + isPublic +
                ", createdAt=" + createdAt +
                ", templateId=" + templateId +
                ", categoryId=" + categoryId +
                ", mediaId=" + mediaId +
                '}';
    }
}
