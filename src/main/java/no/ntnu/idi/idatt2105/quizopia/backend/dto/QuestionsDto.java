package no.ntnu.idi.idatt2105.quizopia.backend.dto;

import java.util.List;

public class QuestionsDto {

    private String questionName;
    private String questionText;
    private String explanations;
    private int question_duration;
    private Boolean isPublic;
    private Long type_id; // Foreign key reference to QuestionType
    private Long difficulty_id; // Foreign key reference to DifficultyLevels
    private Long media_id; // Foreign key reference to MultiMedias
    private List<AnswersDto> answers;

    // Constructors
    public QuestionsDto() {
    }

    public QuestionsDto(String questionName, String questionText, String explanations, int time_left,
            Boolean isPublic, Long type_id, Long difficulty_id, Long media_id, List<AnswersDto> answers) {
        this.questionName = questionName;
        this.questionText = questionText;
        this.explanations = explanations;
        this.question_duration = time_left;
        this.isPublic = isPublic;
        this.type_id = type_id;
        this.difficulty_id = difficulty_id;
        this.media_id = media_id;
        this.answers = answers;
    }

    // Getters and Setters
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

    public List<AnswersDto> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswersDto> answers) {
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "QuestionsDto{" +
                "questionName='" + questionName + '\'' +
                ", questionText='" + questionText + '\'' +
                ", explanations='" + explanations + '\'' +
                ", question_duration=" + question_duration +
                ", isPublic=" + isPublic +
                ", type_id=" + type_id +
                ", difficulty_id=" + difficulty_id +
                ", media_id=" + media_id +
                ", answers=" + answers +
                '}';
    }
}
