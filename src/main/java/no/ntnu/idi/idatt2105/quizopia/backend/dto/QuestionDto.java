package no.ntnu.idi.idatt2105.quizopia.backend.dto;

import java.util.List;

public class QuestionDto {

    private Long questionId;
    private String questionName;
    private String questionText;
    private String explanations;
    private int question_duration;
    private Boolean isPublic;
    private Long typeId; // Foreign key reference to QuestionType
    private Long difficultyId; // Foreign key reference to DifficultyLevel
    private Long mediaId; // Foreign key reference to MultiMedia
    private List<AnswerDto> answers;

    // Constructors
    public QuestionDto() {
    }

    public QuestionDto(Long questionId,String questionName, String questionText, String explanations, int time_left,
            Boolean isPublic, Long typeId, Long difficultyId, Long mediaId, List<AnswerDto> answers) {
        this.questionId = questionId;
        this.questionName = questionName;
        this.questionText = questionText;
        this.explanations = explanations;
        this.question_duration = time_left;
        this.isPublic = isPublic;
        this.typeId = typeId;
        this.difficultyId = difficultyId;
        this.mediaId = mediaId;
        this.answers = answers;
    }

    // Getters and Setters
    public Long getquestionId() {
        return questionId;
    }
    public void setquestionId(Long questionId) {
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

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public Long gettypeId() {
        return typeId;
    }

    public void settypeId(Long typeId) {
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

    public List<AnswerDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDto> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "QuestionDto{" +
                "questionName='" + questionName + '\'' +
                ", questionText='" + questionText + '\'' +
                ", explanations='" + explanations + '\'' +
                ", question_duration=" + question_duration +
                ", isPublic=" + isPublic +
                ", typeId=" + typeId +
                ", difficultyId=" + difficultyId +
                ", mediaId=" + mediaId +
                ", answers=" + answers +
                '}';
    }
}
