package no.ntnu.idi.idatt2105.quizopia.backend.model;

public class MediaType {

    private Long typeId;
    private String description;

    // Constructors
    public MediaType() {
    }

    public MediaType(Long typeId, String description) {
        this.typeId = typeId;
        this.description = description;
    }

    // Getters and Setters
    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "MediaType{" +
                "typeId=" + typeId +
                ", description='" + description + '\'' +
                '}';
    }
}
