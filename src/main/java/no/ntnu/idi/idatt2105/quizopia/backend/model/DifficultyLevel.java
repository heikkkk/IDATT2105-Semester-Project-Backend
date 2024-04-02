package no.ntnu.idi.idatt2105.quizopia.backend.model;

public class DifficultyLevel {

    private Long difficultyId;
    private String difficulty;

    // Constructors
    public DifficultyLevel() {
    }

    public DifficultyLevel(Long difficultyId, String difficulty) {
        this.difficultyId = difficultyId;
        this.difficulty = difficulty;
    }

    // Getters and Setters
    public Long getDifficultyId() {
        return difficultyId;
    }

    public void setDifficultyId(Long difficultyId) {
        this.difficultyId = difficultyId;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return "DifficultyLevel{" +
                "difficultyId=" + difficultyId +
                ", difficulty='" + difficulty + '\'' +
                '}';
    }
}
