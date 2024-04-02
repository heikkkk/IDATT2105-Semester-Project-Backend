package no.ntnu.idi.idatt2105.quizopia.backend.dto;

import java.time.LocalDateTime;
import java.util.List;

public class QuizDto {
    private String title;
    private String description;
    private boolean isPublic;
    private LocalDateTime createdAt;
    private Long template_id; // Foreign key reference to Templates
    private Long category_id; // Foreign key reference to Categories 
    private Long media_id;    // Foreign key reference to Multi_Medias;
    private Long user_id;
    private List<QuestionsDto> questions;

    // Constructor
    public QuizDto() {
    }

    public QuizDto(String title, String description, Boolean isPublic, LocalDateTime createdAt, Long media_id,
            Long category_id, Long template_id, Long user_id, List<QuestionsDto> questions) {
        this.title = title;
        this.description = description;
        this.isPublic = isPublic;
        this.createdAt = createdAt;
        this.media_id = media_id;
        this.category_id = category_id;
        this.template_id = template_id;
        this.user_id = user_id;
        this.questions = questions;
    }

    // Getters and Setters
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

    public LocalDateTime getCreated_at() {
        return createdAt;
    }

    public void setCreated_at(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getMediaId() {
        return media_id;
    }

    public void setMediaId(Long media_id) {
        this.media_id = media_id;
    }

    public Long getCategoryId() {
        return category_id;
    }

    public void setCategoryId(Long category_id) {
        this.category_id = category_id;
    }

    public Long getTemplateId() {
        return template_id;
    }

    public void setTemplateId(Long template_id) {
        this.template_id = template_id;
    }

    public List<QuestionsDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionsDto> questions) {
        this.questions = questions;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
    // Getters and Setters
    // (Getters and Setters are omitted for brevity)

    @Override
    public String toString() {
        return "QuizDto{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isPublic=" + isPublic +
                ", createdAt=" + createdAt +
                ", template_id=" + template_id +
                ", category_id=" + category_id +
                ", media_id=" + media_id +
                ", user_id=" + user_id +
                ", questions=" + questions +
                '}';
    }
}
