package no.ntnu.idi.idatt2105.quizopia.backend.model;

public class TypeCollaborators {

    private Long type_id;
    private String name;

    // Constructors
    public TypeCollaborators() {
    }

    public TypeCollaborators(Long type_id, String name) {
        this.type_id = type_id;
        this.name = name;
    }

    // Getters and Setters
    public Long getTypeId() {
        return type_id;
    }

    public void setTypeId(Long type_id) {
        this.type_id = type_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TypeCollaborators{" +
                "type_id=" + type_id +
                ", name='" + name + '\'' +
                '}';
    }
}
