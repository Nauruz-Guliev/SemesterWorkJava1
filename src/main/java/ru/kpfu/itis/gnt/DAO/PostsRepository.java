package ru.kpfu.itis.gnt.DAO;

import ru.kpfu.itis.gnt.entities.Post;

import java.util.List;
import java.util.Optional;

public interface PostsRepository {
    Optional<List<Post>> findPosts(int limit, int offset);
    Optional<Post> findPostById(int postId);
    boolean updatePost(Post post);
    void addPost(Post post);
}
