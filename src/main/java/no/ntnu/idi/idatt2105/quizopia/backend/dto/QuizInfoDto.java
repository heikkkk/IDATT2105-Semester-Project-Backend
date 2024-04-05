package no.ntnu.idi.idatt2105.quizopia.backend.dto;

/**
 * Used to represent the "minimal" representation of a Quiz.
 */
public class QuizInfoDto {
    private Long quizId;
    private String quizTitle;
    private Long mediaId;
    private String thumbnailFilepath;

    // Constructor
    public QuizInfoDto(Long quizId, String quizTitle, Long mediaId, String thumbnailFilepath) {
        this.quizId = quizId;
        this.quizTitle = quizTitle;
        this.mediaId = mediaId;
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

    public Long getmediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public String getThumbnailFilepath() {
        return thumbnailFilepath;
    }

    public void setThumbnailFilepath(String thumbnailFilepath) {
        this.thumbnailFilepath = thumbnailFilepath;
    }
    
}
