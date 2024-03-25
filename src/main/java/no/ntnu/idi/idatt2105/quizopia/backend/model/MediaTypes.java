package no.ntnu.idi.idatt2105.quizopia.backend.model;

public class MediaTypes {

    private Long type_id;
    private String description;

    // Constructors
    public MediaTypes() {
    }

    public MediaTypes(Long type_id, String description) {
        this.type_id = type_id;
        this.description = description;
    }

    // Getters and Setters
    public Long getTypeId() {
        return type_id;
    }

    public void setTypeId(Long type_id) {
        this.type_id = type_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "MediaTypes{" +
                "type_id=" + type_id +
                ", description='" + description + '\'' +
                '}';
    }
}
