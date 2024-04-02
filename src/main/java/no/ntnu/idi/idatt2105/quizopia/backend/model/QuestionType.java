package no.ntnu.idi.idatt2105.quizopia.backend.model;

public class QuestionType {

    private Long typeId;
    private String type;

    // Constructors
    public QuestionType() {
    }

    public QuestionType(Long typeId, String type) {
        this.typeId = typeId;
        this.type = type;
    }

    // Getters and Setters
    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "QuestionTypes{" +
                "typeId=" + typeId +
                ", type='" + type + '\'' +
                '}';
    }
}
