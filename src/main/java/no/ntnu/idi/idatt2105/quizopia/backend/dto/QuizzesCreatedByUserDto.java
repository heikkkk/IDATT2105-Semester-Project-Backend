package no.ntnu.idi.idatt2105.quizopia.backend.dto;

public class QuizzesCreatedByUserDto {
    private Long quiz_id;
    private String quiz_title;
    private Long media_id;
    private String thumbnail_filepath;

    // Constructor
    public QuizzesCreatedByUserDto(Long quiz_id, String quiz_title, Long media_id, String thumbnail_filepath) {
        this.quiz_id = quiz_id;
        this.quiz_title = quiz_title;
        this.media_id = media_id;
        this.thumbnail_filepath = thumbnail_filepath;
    }

    // Getters
    public Long getQuiz_id() {
        return quiz_id;
    }

    public String getQuiz_title() {
        return quiz_title;
    }

    public Long getMedia_id() {
        return media_id;
    }

    public String getThumbnail_filepath() {
        return thumbnail_filepath;
    }

    // Setters
    public void setQuiz_id(Long quiz_id) {
        this.quiz_id = quiz_id;
    }

    public void setQuiz_title(String quiz_title) {
        this.quiz_title = quiz_title;
    }

    public void setMedia_id(Long media_id) {
        this.media_id = media_id;
    }

    public void setThumbnail_filepath(String thumbnail_filepath) {
        this.thumbnail_filepath = thumbnail_filepath;
    }
}
