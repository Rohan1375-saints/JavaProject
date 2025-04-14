package org.example.projectj3.pojo;

public class Tag {
    private int tagId;
    private String title;


    /**
     * Constructor for Tag
     * @param tagId
     * @param title
     */
    public Tag(int tagId, String title) {
        this.tagId = tagId;
        this.title = title;

    }

    /**
     * Constructor for Tag
     * @param title
     */
    public Tag(String title) {
        this.title = title;
    }

    public Tag() {
    }

    /**
     * Getter for tagId
     * @return tagId
     */
    public int getTagId() {
        return tagId;
    }

    /**
     * Setter for tagId
     * @param tagId
     */
    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    /**
     * Getter for title
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter for title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }


    /**
     * Override toString method
     * @return
     */
    @Override
    public String toString() {
        return "Tag{" +
                "tagId=" + tagId +
                ", title='" + title + '\'' +
                '}';
    }
}