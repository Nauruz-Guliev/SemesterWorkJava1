package ru.kpfu.itis.gnt.entities;

import java.util.Objects;

public class Comment {
    private int id;
    private String text;
    private int post_id;
    private int author_id;
    private String created_at;

    public Comment( String text, int post_id, int author_id) {
        this.text = text;
        this.post_id = post_id;
        this.author_id = author_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment = (Comment) o;
        return getId() == comment.getId() && getPost_id() == comment.getPost_id() && getAuthor_id() == comment.getAuthor_id() && getText().equals(comment.getText());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getText(), getPost_id(), getAuthor_id());
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", post_id=" + post_id +
                ", author_id=" + author_id +
                ", created_at='" + created_at + '\'' +
                '}';
    }
}
