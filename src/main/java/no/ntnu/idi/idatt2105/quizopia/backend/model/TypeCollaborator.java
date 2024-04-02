package no.ntnu.idi.idatt2105.quizopia.backend.model;

public class TypeCollaborator {

    private Long typeId;
    private String name;

    // Constructors
    public TypeCollaborator() {
    }

    public TypeCollaborator(Long typeId, String name) {
        this.typeId = typeId;
        this.name = name;
    }

    // Getters and Setters
    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TypeCollaborator{" +
                "typeId=" + typeId +
                ", name='" + name + '\'' +
                '}';
    }
}
