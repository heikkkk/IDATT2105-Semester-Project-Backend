package no.ntnu.idi.idatt2105.quizopia.backend.model;

public class Questions {

    private Long question_id;
    private String questionName;
    private String questionText;
    private String explanations;
    private Boolean isPublic;
    private Long difficulty_id; // Foreign key reference to DifficultyLevels
    private Long media_id; // Foreign key reference to MultiMedias

    // Constructors
    public Questions() {
    }

    public Questions(Long question_id, String questionName, String questionText, String explanations, Boolean isPublic, Long difficulty_id, Long media_id) {
        this.question_id = question_id;
        this.questionName = questionName;
        this.questionText = questionText;
        this.explanations = explanations;
        this.isPublic = isPublic;
        this.difficulty_id = difficulty_id;
        this.media_id = media_id;
    }

    // Getters and Setters
    public Long getQuestionId() {
        return question_id;
    }

    public void setQuestionId(Long question_id) {
        this.question_id = question_id;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getExplanations() {
        return explanations;
    }

    public void setExplanations(String explanations) {
        this.explanations = explanations;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean publicValue) {
        isPublic = publicValue;
    }

    public Long getDifficultyId() {
        return difficulty_id;
    }

    public void setDifficultyId(Long difficulty_id) {
        this.difficulty_id = difficulty_id;
    }

    public Long getMediaId() {
        return media_id;
    }

    public void setMediaId(Long media_id) {
        this.media_id = media_id;
    }

    @Override
    public String toString() {
        return "Questions{" +
                "question_id=" + question_id +
                ", questionName='" + questionName + '\'' +
                ", questionText='" + questionText + '\'' +
                ", explanations='" + explanations + '\'' +
                ", isPublic=" + isPublic +
                ", difficulty_id=" + difficulty_id +
                ", media_id=" + media_id +
                '}';
    }
}
