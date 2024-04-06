package no.ntnu.idi.idatt2105.quizopia.backend.dto;

/**
 * Used to represent the "minimal" representation of a Quiz.
 */
public class QuizInfoDto {
    private Long quizId;
    private String quizTitle;
    private Long categoryId;
    private String author;
    private String thumbnailFilepath;

    // Constructor
    public QuizInfoDto(Long quizId, String quizTitle, Long categoryId, String author, String thumbnailFilepath) {
        this.quizId = quizId;
        this.quizTitle = quizTitle;
        this.categoryId = categoryId;
        this.author = author;
        this.thumbnailFilepath = thumbnailFilepath;
    }

    // Getters and Setters
    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quiz_title) {
        this.quizTitle = quiz_title;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getThumbnailFilepath() {
        return thumbnailFilepath;
    }

    public void setThumbnailFilepath(String thumbnailFilepath) {
        this.thumbnailFilepath = thumbnailFilepath;
    }
    
}
