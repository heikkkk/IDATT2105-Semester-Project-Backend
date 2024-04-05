package no.ntnu.idi.idatt2105.quizopia.backend.dto;

import java.time.LocalDateTime;
import java.util.List;

public class QuizDto {
    private Long quizId;
    private String title;
    private String description;
    private boolean isPublic;
    private LocalDateTime createdAt;
    private Long templateId; // Foreign key reference to Template
    private Long categoryId; // Foreign key reference to Category 
    private Long mediaId;    // Foreign key reference to Multi_Medias;
    private Long userId;
    private List<QuestionDto> questions;

    // Constructor
    public QuizDto() {
    }

    public QuizDto(Long quizId, String title, String description, Boolean isPublic, LocalDateTime createdAt, Long mediaId,
            Long categoryId, Long templateId, Long userId, List<QuestionDto> questions) {
        this.quizId = quizId;
        this.title = title;
        this.description = description;
        this.isPublic = isPublic;
        this.createdAt = createdAt;
        this.mediaId = mediaId;
        this.categoryId = categoryId;
        this.templateId = templateId;
        this.userId = userId;
        this.questions = questions;
    }

    public QuizDto(String title, String description, Boolean isPublic, LocalDateTime createdAt, Long mediaId,
            Long categoryId, Long templateId, Long userId, List<QuestionDto> questions) {
        this.title = title;
        this.description = description;
        this.isPublic = isPublic;
        this.createdAt = createdAt;
        this.mediaId = mediaId;
        this.categoryId = categoryId;
        this.templateId = templateId;
        this.userId = userId;
        this.questions = questions;
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

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }

    public List<QuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionDto> questions) {
        this.questions = questions;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "QuizDto{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isPublic=" + isPublic +
                ", createdAt=" + createdAt +
                ", templateId=" + templateId +
                ", categoryId=" + categoryId +
                ", mediaId=" + mediaId +
                ", userId=" + userId +
                ", questions=" + questions +
                '}';
    }
}
