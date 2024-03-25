package no.ntnu.idi.idatt2105.quizopia.backend.model;

public class Collaborators {

    private Long user_id; // Foreign key reference to Users
    private Long quiz_id; // Foreign key reference to Quiz
    private Long type_id; // Foreign key reference to TypeCollaborators

    // Constructors
    public Collaborators() {
    }

    public Collaborators(Long user_id, Long quiz_id, Long type_id) {
        this.user_id = user_id;
        this.quiz_id = quiz_id;
        this.type_id = type_id;
    }

    // Getters and Setters
    public Long getUserId() {
        return user_id;
    }

    public void setUserId(Long user_id) {
        this.user_id = user_id;
    }

    public Long getQuizId() {
        return quiz_id;
    }

    public void setQuizId(Long quizId) {
        this.quiz_id = quizId;
    }

    public Long getTypeId() {
        return type_id;
    }

    public void setTypeId(Long type_id) {
        this.type_id = type_id;
    }

    @Override
    public String toString() {
        return "Collaborators{" +
                "user_id=" + user_id +
                ", quiz_id=" + quiz_id +
                ", type_id=" + type_id +
                '}';
    }
}
