package ru.kpfu.itis.gnt.services.implementations;

import ru.kpfu.itis.gnt.DAO.implementations.PostsRepositoryImpl;
import ru.kpfu.itis.gnt.DAO.implementations.UsersRepositoryJDBCTemplateImpl;
import ru.kpfu.itis.gnt.entities.Post;
import ru.kpfu.itis.gnt.entities.User;
import ru.kpfu.itis.gnt.exceptions.DBException;
import ru.kpfu.itis.gnt.services.PostsService;

import java.util.List;

public class PostsServiceImpl implements PostsService {

    private final PostsRepositoryImpl postsRepository;

    private final UsersRepositoryJDBCTemplateImpl userDao;

    public PostsServiceImpl(PostsRepositoryImpl postsRepository, UsersRepositoryJDBCTemplateImpl userDao) {
        this.postsRepository = postsRepository;
        this.userDao = userDao;
    }

    @Override
    public List<Post> getPosts(int limit, int offset) throws DBException {
        return postsRepository.findPosts(limit, offset)
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

    @Override
    public User getPostAuthor(Post post) throws DBException {
        return userDao.findById(post.getId()).orElseThrow(
                () -> new DBException("There is no user with such an index"));

    }
}
