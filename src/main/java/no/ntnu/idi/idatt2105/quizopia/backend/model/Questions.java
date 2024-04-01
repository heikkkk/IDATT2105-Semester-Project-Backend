package no.ntnu.idi.idatt2105.quizopia.backend.model;

public class Questions {

    private Long question_id;
    private String questionName;
    private String questionText;
    private String explanations;
    private int question_duration;
    private Boolean isPublic;
    private Long type_id; // Foreign key reference to QuestionType
    private Long difficulty_id; // Foreign key reference to DifficultyLevels
    private Long media_id; // Foreign key reference to MultiMedias

    // Constructors
    public Questions() {
    }

    public Questions(Long question_id, String questionName, String questionText, String explanations, int question_duration, Boolean isPublic, Long type_id, Long difficulty_id, Long media_id) {
        this.question_id = question_id;
        this.questionName = questionName;
        this.questionText = questionText;
        this.explanations = explanations;
        this.question_duration = question_duration;
        this.isPublic = isPublic;
        this.type_id = type_id;
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

    public int getQuestion_duration() {
        return question_duration;
    }

    public void setQuestion_duration(int question_duration) {
        this.question_duration = question_duration;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean publicValue) {
        isPublic = publicValue;
    }

    public Long getType_id() {
        return type_id;
    }

    public void setType_id(Long type_id) {
        this.type_id = type_id;
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
                ", timeLeft='" + question_duration + '\'' +
                ", isPublic=" + isPublic +
                ", typeId=" + type_id +
                ", difficulty_id=" + difficulty_id +
                ", media_id=" + media_id +
                '}';
    }
}
