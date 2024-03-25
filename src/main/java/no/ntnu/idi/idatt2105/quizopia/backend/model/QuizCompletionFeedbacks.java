package no.ntnu.idi.idatt2105.quizopia.backend.model;

public class QuizCompletionFeedbacks {

    private Long quiz_id; // Foreign key reference to Quiz
    private Long feedback_id; // Foreign key reference to CompletionFeedbacks
    private Integer scoreLowerBound;
    private Integer scoreUpperBound;

    // Constructors
    public QuizCompletionFeedbacks() {
    }

    public QuizCompletionFeedbacks(Long quiz_id, Long feedback_id, Integer scoreLowerBound, Integer scoreUpperBound) {
        this.quiz_id = quiz_id;
        this.feedback_id = feedback_id;
        this.scoreLowerBound = scoreLowerBound;
        this.scoreUpperBound = scoreUpperBound;
    }

    // Getters and Setters
    public Long getQuizId() {
        return quiz_id;
    }

    public void setQuizId(Long quiz_id) {
        this.quiz_id = quiz_id;
    }

    public Long getFeedbackId() {
        return feedback_id;
    }

    public void setFeedbackId(Long feedback_id) {
        this.feedback_id = feedback_id;
    }

    public Integer getScoreLowerBound() {
        return scoreLowerBound;
    }

    public void setScoreLowerBound(Integer scoreLowerBound) {
        this.scoreLowerBound = scoreLowerBound;
    }

    public Integer getScoreUpperBound() {
        return scoreUpperBound;
    }

    public void setScoreUpperBound(Integer scoreUpperBound) {
        this.scoreUpperBound = scoreUpperBound;
    }

    // toString method
    @Override
    public String toString() {
        return "QuizCompletionFeedbacks{" +
                "quiz_id=" + quiz_id +
                ", feedback_id=" + feedback_id +
                ", scoreLowerBound=" + scoreLowerBound +
                ", scoreUpperBound=" + scoreUpperBound +
                '}';
    }
}
