package no.ntnu.idi.idatt2105.quizopia.backend.model;

import java.time.LocalDateTime;

public class Templates {

    private Long template_id;
    private String name;
    private String description;
    private String filepath;
    private LocalDateTime createdAt;
    private Long user_id; // Foreign key reference to Users

    // Constructors
    public Templates() {
    }

    public Templates(Long template_id, String name, String description, String filepath, LocalDateTime createdAt, Long user_id) {
        this.template_id = template_id;
        this.name = name;
        this.description = description;
        this.filepath = filepath;
        this.createdAt = createdAt;
        this.user_id = user_id;
    }

    // Getters and Setters
    public Long getTemplateId() {
        return template_id;
    }

    public void setTemplateId(Long template_id) {
        this.template_id = template_id;
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
        return user_id;
    }

    public void setUserId(Long user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Templates{" +
                "template_id=" + template_id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", filepath='" + filepath + '\'' +
                ", createdAt=" + createdAt +
                ", user_id=" + user_id +
                '}';
    }
}
