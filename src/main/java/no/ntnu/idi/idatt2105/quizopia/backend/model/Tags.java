package no.ntnu.idi.idatt2105.quizopia.backend.model;

public class Tags {

    private Long tag_id;
    private String tag;
    private Long user_id; // Foreign key reference to Users

    // Constructors
    public Tags() {
    }

    public Tags(Long tag_id, String tag, Long user_id) {
        this.tag_id = tag_id;
        this.tag = tag;
        this.user_id = user_id;
    }

    // Getters and Setters
    public Long getTagId() {
        return tag_id;
    }

    public void setTagId(Long tag_id) {
        this.tag_id = tag_id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Long getUserId() {
        return user_id;
    }

    public void setUserId(Long user_id) {
        this.user_id = user_id;
    }

    // toString method
    @Override
    public String toString() {
        return "Tags{" +
                "tag_id=" + tag_id +
                ", tag='" + tag + '\'' +
                ", user_id=" + user_id +
                '}';
    }
}
