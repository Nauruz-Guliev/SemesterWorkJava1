package ru.kpfu.itis.gnt.DAO;

import ru.kpfu.itis.gnt.entities.Post;
import ru.kpfu.itis.gnt.exceptions.EmptyResultDbException;

import java.util.List;
import java.util.Optional;

public interface PostsRepository {
    Optional<List<Post>> findPosts(int limit, int offset) throws EmptyResultDbException;
    Optional<Post> findPostById(int postId) throws EmptyResultDbException;
    boolean updatePost(Post post);
    void addPost(Post post);
}
