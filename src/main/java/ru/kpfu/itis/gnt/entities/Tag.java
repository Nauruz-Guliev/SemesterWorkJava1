package ru.kpfu.itis.gnt.entities;

public class Tag {
    private int id;
    private int post_id;
    private int tag_name_id;

    private String name;

    public Tag(int post_id, int tag_name_id) {
        this.post_id = post_id;
        this.tag_name_id = tag_name_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public int getTag_name_id() {
        return tag_name_id;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", post_id=" + post_id +
                ", tag_name_id=" + tag_name_id +
                ", name='" + name + '\'' +
                '}';
    }

    public void setTag_name_id(int tag_name_id) {
        this.tag_name_id = tag_name_id;
    }
}
