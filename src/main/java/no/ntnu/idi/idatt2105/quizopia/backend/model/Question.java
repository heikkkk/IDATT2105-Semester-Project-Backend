package no.ntnu.idi.idatt2105.quizopia.backend.model;

public class Question {

    private Long questionId;
    private String questionName;
    private String questionText;
    private String explanations;
    private int questionDuration;
    private Boolean isPublic;
    private Long typeId; // Foreign key reference to QuestionType
    private Long difficultyId; // Foreign key reference to DifficultyLevel
    private Long mediaId; // Foreign key reference to MultiMedia

    // Constructors
    public Question() {
    }

    public Question(Long questionId, String questionName, String questionText, String explanations, int questionDuration, Boolean isPublic, Long typeId, Long difficultyId, Long mediaId) {
        this.questionId = questionId;
        this.questionName = questionName;
        this.questionText = questionText;
        this.explanations = explanations;
        this.questionDuration = questionDuration;
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

    public int getQuestionDuration() {
        return questionDuration;
    }

    public void setQuestionDuration(int questionDuration) {
        this.questionDuration = questionDuration;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean publicValue) {
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
                ", questionDuration='" + questionDuration + '\'' +
                ", timeLeft='" + questionDuration + '\'' +
                ", isPublic=" + isPublic +
                ", typeId=" + typeId +
                ", difficultyId=" + difficultyId +
                ", mediaId=" + mediaId +
                '}';
    }
}
