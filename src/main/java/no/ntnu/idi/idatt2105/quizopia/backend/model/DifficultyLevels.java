package no.ntnu.idi.idatt2105.quizopia.backend.model;

public class DifficultyLevels {

    private Long difficulty_id;
    private String difficulty;

    // Constructors
    public DifficultyLevels() {
    }

    public DifficultyLevels(Long difficulty_id, String difficulty) {
        this.difficulty_id = difficulty_id;
        this.difficulty = difficulty;
    }

    // Getters and Setters
    public Long getDifficultyId() {
        return difficulty_id;
    }

    public void setDifficultyId(Long difficulty_id) {
        this.difficulty_id = difficulty_id;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public String toString() {
        return "DifficultyLevels{" +
                "difficulty_id=" + difficulty_id +
                ", difficulty='" + difficulty + '\'' +
                '}';
    }
}
