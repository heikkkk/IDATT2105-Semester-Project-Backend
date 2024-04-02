package no.ntnu.idi.idatt2105.quizopia.backend.model;

public class Collaborator {

    private Long userId; // Foreign key reference to Users
    private Long quizId; // Foreign key reference to Quiz
    private Long typeId; // Foreign key reference to TypeCollaborator

    // Constructors
    public Collaborator() {
    }

    public Collaborator(Long userId, Long quizId, Long typeId) {
        this.userId = userId;
        this.quizId = quizId;
        this.typeId = typeId;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "Collaborator{" +
                "userId=" + userId +
                ", quizId=" + quizId +
                ", typeId=" + typeId +
                '}';
    }
}
