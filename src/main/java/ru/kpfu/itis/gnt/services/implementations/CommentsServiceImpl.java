package ru.kpfu.itis.gnt.services.implementations;

import ru.kpfu.itis.gnt.DAO.implementations.CommentsRepositoryImpl;
import ru.kpfu.itis.gnt.DAO.implementations.PostsRepositoryImpl;
import ru.kpfu.itis.gnt.DAO.implementations.UsersRepositoryJDBCTemplateImpl;
import ru.kpfu.itis.gnt.entities.Comment;
import ru.kpfu.itis.gnt.entities.Post;
import ru.kpfu.itis.gnt.entities.User;
import ru.kpfu.itis.gnt.exceptions.DBException;
import ru.kpfu.itis.gnt.services.CommentsService;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommentsServiceImpl implements CommentsService {

    private final PostsRepositoryImpl postsDao;

    private final UsersRepositoryJDBCTemplateImpl userDao;

    private final CommentsRepositoryImpl commentsDao;

    public CommentsServiceImpl(ServletContext context) {
        this.postsDao = (PostsRepositoryImpl) context.getAttribute("POSTS_DAO");
        this.userDao = (UsersRepositoryJDBCTemplateImpl) context.getAttribute("USERDAO");
        this.commentsDao = (CommentsRepositoryImpl) context.getAttribute("COMMENTS_DAO");
        ;
    }

    @Override
    public List<Comment> getAllComments(Post post) throws DBException {
        return commentsDao.findAllComments(post.getId()).orElseThrow(
                () -> new DBException("There is no comments.")
        );
    }

    @Override
    public boolean addComment(Comment comment) {
        return commentsDao.addComment(comment);
    }


    public User getCommentAuthor(Post post) throws DBException {
        return userDao.findById(post.getAuthor_id()).orElseThrow(
                () -> new DBException("Couldn't find the author.")
        );
    }

    /**
     У каждого комментария обязательно найдётся автор, так как в бд комментарий не может
     храниться без привязки к автору.
     */
    public HashMap<Comment, User> getCommentAuthors(List<Comment> comments) {
        HashMap<Comment, User> commentUserHashMap = new HashMap<>();
        for (Comment comment : comments) {
            commentUserHashMap.put(comment, userDao.findById(comment.getAuthor_id()).get());

        }
        return commentUserHashMap;
    }
}
