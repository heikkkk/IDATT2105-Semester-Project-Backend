package no.ntnu.idi.idatt2105.quizopia.backend.model;

public class QuizCompletionFeedback {

    private Long quizId; // Foreign key reference to Quiz
    private Long feedbackId; // Foreign key reference to CompletionFeedback
    private Integer scoreLowerBound;
    private Integer scoreUpperBound;

    // Constructors
    public QuizCompletionFeedback() {
    }

    public QuizCompletionFeedback(Long quizId, Long feedbackId, Integer scoreLowerBound, Integer scoreUpperBound) {
        this.quizId = quizId;
        this.feedbackId = feedbackId;
        this.scoreLowerBound = scoreLowerBound;
        this.scoreUpperBound = scoreUpperBound;
    }

    // Getters and Setters
    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
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
        return "QuizCompletionFeedback{" +
                "quizId=" + quizId +
                ", feedbackId=" + feedbackId +
                ", scoreLowerBound=" + scoreLowerBound +
                ", scoreUpperBound=" + scoreUpperBound +
                '}';
    }
}
