package no.ntnu.idi.idatt2105.quizopia.backend.model;

public class QuizQuestions {

    private Long question_id; // Foreign key reference to Questions
    private Long quiz_id; // Foreign key reference to Quiz

    // Constructors
    public QuizQuestions() {
    }

    public QuizQuestions(Long question_id, Long quiz_id) {
        this.question_id = question_id;
        this.quiz_id = quiz_id;
    }

    // Getters and Setters
    public Long getQuestionId() {
        return question_id;
    }

    public void setQuestionId(Long question_id) {
        this.question_id = question_id;
    }

    public Long getQuizId() {
        return quiz_id;
    }

    public void setQuizId(Long quiz_id) {
        this.quiz_id = quiz_id;
    }

    @Override
    public String toString() {
        return "Questions{" +
                "question_id=" + question_id +
                ", quiz_id='" + quiz_id + '\'' +
                '}';
    }
}
