package no.ntnu.idi.idatt2105.quizopia.backend.model;

public class Users {

    private Long user_id;
    private String name;
    private String email;
    private String password;
    private Long role_id; // Foreign key reference to Roles

    // Constructors
    public Users() {
    }

    public Users(Long user_id, String name, String email, String password, Long role_id) {
        this.user_id = user_id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role_id = role_id;
    }

    // Getters and Setters
    public Long getUserId() {
        return user_id;
    }

    public void setUserId(Long user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getRoleId() {
        return role_id;
    }

    public void setRoleId(Long role_id) {
        this.role_id = role_id;
    }

    @Override
    public String toString() {
        return "Users{" +
                "user_id=" + user_id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role_id='" + role_id + '\'' +
                '}';
    }
}
