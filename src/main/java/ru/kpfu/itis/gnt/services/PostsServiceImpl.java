package ru.kpfu.itis.gnt.services;

import ru.kpfu.itis.gnt.DAO.PostsRepositoryImpl;
import ru.kpfu.itis.gnt.DAO.UsersRepositoryJDBCTemplateImpl;
import ru.kpfu.itis.gnt.entities.Post;
import ru.kpfu.itis.gnt.entities.User;
import ru.kpfu.itis.gnt.exceptions.DBException;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostsServiceImpl implements PostsService {

    private final PostsRepositoryImpl postsRepository;

    private final UsersRepositoryJDBCTemplateImpl userDao;

    public PostsServiceImpl(ServletContext context) {
        this.postsRepository = (PostsRepositoryImpl) context.getAttribute("POSTS_DAO");
        this.userDao = (UsersRepositoryJDBCTemplateImpl) context.getAttribute("USERDAO");
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

    @Override
    public User getPostAuthor(Post post) throws DBException {
        return userDao.findById(post.getId()).orElseThrow(
                () -> new DBException("There is no user with such an index"));

    }
}
