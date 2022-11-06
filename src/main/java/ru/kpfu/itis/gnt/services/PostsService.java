package ru.kpfu.itis.gnt.services;

import ru.kpfu.itis.gnt.entities.Post;
import ru.kpfu.itis.gnt.entities.User;
import ru.kpfu.itis.gnt.exceptions.DBException;

import java.util.List;

public interface PostsService {

    List<Post> getAllPosts() throws DBException;

    Post getPostById(int postId) throws DBException;
    boolean addPost(Post post);
    boolean editPost(Post post);

    User getPostAuthor(Post post) throws DBException;
}
