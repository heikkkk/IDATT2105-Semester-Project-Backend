package no.ntnu.idi.idatt2105.quizopia.backend.model;

public class QuizQuestion {

    private Long questionId; // Foreign key reference to Question
    private Long quizId; // Foreign key reference to Quiz

    // Constructors
    public QuizQuestion() {
    }

    public QuizQuestion(Long questionId, Long quizId) {
        this.questionId = questionId;
        this.quizId = quizId;
    }

    // Getters and Setters
    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", quizId='" + quizId + '\'' +
                '}';
    }
}
