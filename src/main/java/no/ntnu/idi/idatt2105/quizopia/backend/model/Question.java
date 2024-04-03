package no.ntnu.idi.idatt2105.quizopia.backend.model;

public class Question {

    private Long questionId;
    private String questionName;
    private String questionText;
    private String explanations;
    private int question_duration;
    private Boolean isPublic;
    private Long typeId; // Foreign key reference to QuestionType
    private Long difficultyId; // Foreign key reference to DifficultyLevel
    private Long mediaId; // Foreign key reference to MultiMedia

    // Constructors
    public Question() {
    }

    public Question(Long questionId, String questionName, String questionText, String explanations, int question_duration, Boolean isPublic, Long typeId, Long difficultyId, Long mediaId) {
        this.questionId = questionId;
        this.questionName = questionName;
        this.questionText = questionText;
        this.explanations = explanations;
        this.question_duration = question_duration;
        this.isPublic = isPublic;
        this.typeId = typeId;
        this.difficultyId = difficultyId;
        this.mediaId = mediaId;
    }

    // Getters and Setters
    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
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

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getDifficultyId() {
        return difficultyId;
    }

    public void setDifficultyId(Long difficultyId) {
        this.difficultyId = difficultyId;
    }

    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", questionName='" + questionName + '\'' +
                ", questionText='" + questionText + '\'' +
                ", explanations='" + explanations + '\'' +
                ", timeLeft='" + question_duration + '\'' +
                ", isPublic=" + isPublic +
                ", typeId=" + typeId +
                ", difficultyId=" + difficultyId +
                ", mediaId=" + mediaId +
                '}';
    }
}
