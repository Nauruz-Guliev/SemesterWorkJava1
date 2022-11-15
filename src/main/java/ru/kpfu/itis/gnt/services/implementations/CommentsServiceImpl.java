package ru.kpfu.itis.gnt.services.implementations;

import ru.kpfu.itis.gnt.DAO.implementations.CommentsRepositoryImpl;
import ru.kpfu.itis.gnt.DAO.implementations.PostsRepositoryImpl;
import ru.kpfu.itis.gnt.DAO.implementations.UsersRepositoryJDBCTemplateImpl;
import ru.kpfu.itis.gnt.entities.Comment;
import ru.kpfu.itis.gnt.entities.Post;
import ru.kpfu.itis.gnt.entities.User;
import ru.kpfu.itis.gnt.exceptions.DBException;
import ru.kpfu.itis.gnt.exceptions.EmptyResultDbException;
import ru.kpfu.itis.gnt.services.CommentsService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class CommentsServiceImpl implements CommentsService {

    private final PostsRepositoryImpl postsDao;

    private final UsersRepositoryJDBCTemplateImpl userDao;

    private final CommentsRepositoryImpl commentsDao;

    public CommentsServiceImpl(PostsRepositoryImpl postsDao, UsersRepositoryJDBCTemplateImpl userDao, CommentsRepositoryImpl commentsDao) {
      this.postsDao = postsDao;
      this.userDao = userDao;
      this.commentsDao = commentsDao;
    }

    @Override
    public List<Comment> getComments(Post post, int limit, int offset) throws DBException {
        return commentsDao.findComments(post.getId(), limit, offset).orElseThrow(
                () -> new EmptyResultDbException("There is no comments.")
        );
    }

    public int getCommentsCount(int post_id) throws DBException {
        return commentsDao.getCommentCount(post_id).orElseThrow(
                () -> new EmptyResultDbException("There is no comments!")
        );
    }

    @Override
    public boolean addComment(Comment comment) throws DBException {
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

     почему-то не работает сортировка по дате.
     */
    public HashMap<Comment, User> getCommentAuthors(List<Comment> comments) throws EmptyResultDbException {
        Map<Comment, User> commentUserHashMap = new HashMap<>();
        for (Comment comment : comments) {
            commentUserHashMap.put(comment, userDao.findById(comment.getAuthor_id()).get());
        }


        Comparator<Comment> byDate = (Comment c1, Comment c2) -> {
            return (c1.getCreated_at().substring(0,19)).compareTo(
                             (c2.getCreated_at().substring(0,19)));
        };

        return commentUserHashMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey(byDate))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (el1, el2) -> el1, HashMap::new));
    }
}
