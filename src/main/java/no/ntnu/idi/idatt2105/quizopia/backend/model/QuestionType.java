package no.ntnu.idi.idatt2105.quizopia.backend.model;

public class QuestionType {

    private Long type_id;
    private String type;

    // Constructors
    public QuestionType() {
    }

    public QuestionType(Long type_id, String type) {
        this.type_id = type_id;
        this.type = type;
    }

    // Getters and Setters
    public Long getTypeId() {
        return type_id;
    }

    public void setTypeId(Long type_id) {
        this.type_id = type_id;
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
                "type_id=" + type_id +
                ", type='" + type + '\'' +
                '}';
    }
}
