package ru.kpfu.itis.gnt.services;

import ru.kpfu.itis.gnt.DAO.PostsRepositoryImpl;
import ru.kpfu.itis.gnt.entities.Post;
import ru.kpfu.itis.gnt.exceptions.DBException;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostsServiceImpl implements PostsService {

    private final PostsRepositoryImpl postsRepository;

    public PostsServiceImpl(ServletContext context) {
        this.postsRepository = (PostsRepositoryImpl) context.getAttribute("POSTS_DAO");
    }

    @Override
    public List<Post> getAllPosts() throws DBException {
        return postsRepository.findAllPosts()
                .orElseThrow(() -> new DBException("No posts were found"));
    }

    @Override
    public Post getPostById(int postId) throws DBException {

        return postsRepository.findPostById(postId)
                .orElseThrow(() -> new DBException("Post was not found"));
    }

    @Override
    public boolean addPost(Post post) {
        return postsRepository.addPost(post);
    }

    @Override
    public boolean editPost(Post post) {
        return postsRepository.updatePost(post);
    }
}
