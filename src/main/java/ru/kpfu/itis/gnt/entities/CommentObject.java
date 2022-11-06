package ru.kpfu.itis.gnt.entities;

public class CommentObject {
    private String text;
    private int post_id;
    private String  authorName;
    private String created_at;

    public CommentObject( String text, int post_id, String authorName, String created_at) {
        this.text = text;
        this.post_id = post_id;
        this.authorName = authorName;
        this.created_at = created_at;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "CommentObject{" +
                ", text='" + text + '\'' +
                ", post_id=" + post_id +
                ", authorName=" + authorName +
                ", created_at='" + created_at + '\'' +
                '}';
    }
}
