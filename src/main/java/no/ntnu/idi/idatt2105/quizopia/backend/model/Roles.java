package no.ntnu.idi.idatt2105.quizopia.backend.model;

public class Roles {

    private Long role_id;
    private String type;

    // Constructors
    public Roles() {
    }

    public Roles(Long role_id, String type) {
        this.role_id = role_id;
        this.type = type;
    }

    // Getters and Setters
    public Long getRoleId() {
        return role_id;
    }

    public void setRoleId(Long role_id) {
        this.role_id = role_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Roles{" +
                "role_id=" + role_id +
                ", type='" + type + '\'' +
                '}';
    }
}
