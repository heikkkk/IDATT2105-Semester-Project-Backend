package no.ntnu.idi.idatt2105.quizopia.backend.dto;

public class QuizInfoDto {
    private Long quizId;
    private String quiz_title;
    private Long mediaId;
    private String thumbnail_filepath;

    // Constructor
    public QuizInfoDto(Long quizId, String quiz_title, Long mediaId, String thumbnail_filepath) {
        this.quizId = quizId;
        this.quiz_title = quiz_title;
        this.mediaId = mediaId;
        this.thumbnail_filepath = thumbnail_filepath;
    }

    // Getters
    public Long getquizId() {
        return quizId;
    }

    public String getQuiz_title() {
        return quiz_title;
    }

    public Long getmediaId() {
        return mediaId;
    }

    public String getThumbnail_filepath() {
        return thumbnail_filepath;
    }

    // Setters
    public void setquizId(Long quizId) {
        this.quizId = quizId;
    }

    public void setQuiz_title(String quiz_title) {
        this.quiz_title = quiz_title;
    }

    public void setmediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public void setThumbnail_filepath(String thumbnail_filepath) {
        this.thumbnail_filepath = thumbnail_filepath;
    }
}
