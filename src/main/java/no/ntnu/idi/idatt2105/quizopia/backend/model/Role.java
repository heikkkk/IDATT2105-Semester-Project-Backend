package no.ntnu.idi.idatt2105.quizopia.backend.model;

public class Role {

    private Long roleId;
    private String type;

    // Constructors
    public Role() {
    }

    public Role(Long roleId, String type) {
        this.roleId = roleId;
        this.type = type;
    }

    // Getters and Setters
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", type='" + type + '\'' +
                '}';
    }
}
