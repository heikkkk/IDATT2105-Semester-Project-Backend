package no.ntnu.idi.idatt2105.quizopia.backend.model;

public class Tag {

    private Long tagId;
    private String tag;
    private Long userId; // Foreign key reference to Users

    // Constructors
    public Tag() {
    }

    public Tag(Long tagId, String tag, Long userId) {
        this.tagId = tagId;
        this.tag = tag;
        this.userId = userId;
    }

    // Getters and Setters
    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // toString method
    @Override
    public String toString() {
        return "Tag{" +
                "tagId=" + tagId +
                ", tag='" + tag + '\'' +
                ", userId=" + userId +
                '}';
    }
}
