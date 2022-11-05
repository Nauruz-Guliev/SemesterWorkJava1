package ru.kpfu.itis.gnt.DAO;

import ru.kpfu.itis.gnt.entities.Post;
import ru.kpfu.itis.gnt.entities.User;

import java.util.List;
import java.util.Optional;

public interface PostsRepository {
    Optional<List<Post>> findAllPosts();
    Optional<Post> findPostById(int postId);
    boolean updatePost(Post post);
}