package ru.kpfu.itis.gnt.entities;

public class Post {
    private int id;
    private String title;
    private String body;
    private int likes;
    private int author_id;
    private int category_id;

    public Post(String title, String body, int likes, int author_id, int category_id) {
        this.id = -1;
        this.title = title;
        this.body = body;
        this.likes = likes;
        this.author_id = author_id;
        this.category_id = category_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
}
