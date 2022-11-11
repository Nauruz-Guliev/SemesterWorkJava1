package ru.kpfu.itis.gnt.services.implementations;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.kpfu.itis.gnt.DAO.TagNamesRepository;
import ru.kpfu.itis.gnt.DAO.implementations.PostsRepositoryImpl;
import ru.kpfu.itis.gnt.DAO.implementations.TagsRepositoryImpl;
import ru.kpfu.itis.gnt.DAO.implementations.UsersRepositoryJDBCTemplateImpl;
import ru.kpfu.itis.gnt.entities.Post;
import ru.kpfu.itis.gnt.entities.Tag;
import ru.kpfu.itis.gnt.entities.TagName;
import ru.kpfu.itis.gnt.entities.User;
import ru.kpfu.itis.gnt.exceptions.DBException;
import ru.kpfu.itis.gnt.services.PostsService;
import ru.kpfu.itis.gnt.validators.PostValidator;

import java.util.ArrayList;
import java.util.List;

public class PostsServiceImpl implements PostsService {

    private final PostsRepositoryImpl postsDao;

    private final UsersRepositoryJDBCTemplateImpl userDao;
    private final TagsRepositoryImpl tagsDao;

    private final TagNamesRepository tagNamesDao;

    public PostsServiceImpl(PostsRepositoryImpl postsDao, UsersRepositoryJDBCTemplateImpl userDao, TagsRepositoryImpl tagsDao, TagNamesRepository tagNamesDao) {
        this.postsDao = postsDao;
        this.userDao = userDao;
        this.tagsDao = tagsDao;
        this.tagNamesDao = tagNamesDao;
    }

    public List<Post> getMostPopularPosts() throws DBException {
        return postsDao.findMostPopular().orElseThrow(
                () -> new DBException("No posts found")
        );
    }

    @Override
    public List<Post> getPosts(int limit, int offset) throws DBException {
        return postsDao.findPosts(limit, offset)
                .orElseThrow(() -> new DBException("No posts were found"));
    }

    @Override
    public Post getPostById(int postId) throws DBException {
        return postsDao.findPostById(postId)
                .orElseThrow(() -> new DBException("Post was not found"));
    }

    @Override
    public boolean addPost(Post post, ArrayList<String> tagNames) throws DBException, DuplicateKeyException, EmptyResultDataAccessException {
        if (PostValidator.areFieldsValid(post)) {
            postsDao.addPost(post);
        } else {
            return false;
        }

        int postId = post.getId();
        if (postId > 0) {
            for (String tagName : tagNames) {
                int tagId = tagNamesDao.findTagNameByName(tagName).get();
                if (tagId <= 0) {
                    tagNamesDao.addNewTagName(tagName);
                    tagId = tagNamesDao.findTagNameByName(tagName).get();
                }
                tagsDao.addTag(postId, tagId);
            }
        } else {
            throw new DBException("Added post was not found");
        }
        return true;
    }

    @Override
    public boolean editPost(Post post) {
        return postsDao.updatePost(post);
    }

    @Override
    public User getPostAuthor(Post post) throws DBException {
        return userDao.findById(post.getAuthor_id()).orElseThrow(
                () -> new DBException("There is no user with such an index"));

    }
}
