package ru.kpfu.itis.gnt.services.implementations;

import ru.kpfu.itis.gnt.DAO.implementations.CommentsRepositoryImpl;
import ru.kpfu.itis.gnt.DAO.implementations.LikesRepositoryImpl;
import ru.kpfu.itis.gnt.DAO.implementations.PostsRepositoryImpl;
import ru.kpfu.itis.gnt.DAO.implementations.UsersRepositoryJDBCTemplateImpl;
import ru.kpfu.itis.gnt.exceptions.DBException;
import ru.kpfu.itis.gnt.services.LikesService;


public class LikesServiceImpl implements LikesService {

    private final UsersRepositoryJDBCTemplateImpl usersDao;
    private final LikesRepositoryImpl likesDao;
    private final PostsRepositoryImpl postsDao;
    private final CommentsRepositoryImpl commentsDao;

    public LikesServiceImpl(UsersRepositoryJDBCTemplateImpl usersDao, LikesRepositoryImpl likesDao, PostsRepositoryImpl postsDao, CommentsRepositoryImpl commentsDao) {
        this.usersDao = usersDao;
        this.likesDao = likesDao;
        this.postsDao = postsDao;
        this.commentsDao = commentsDao;
    }


    @Override
    public void likeComment(int comment_id, int user_id) throws DBException {
        if(!likesDao.likeComment(user_id, comment_id)) {
            throw new DBException("Couldn't like the comment");
        }
    }

    @Override
    public boolean likePost(int post_id, int user_id) {
        if (!likesDao.isPostLikePresent(user_id, post_id)) {
            return likesDao.likePost(user_id, post_id);
        } else {
            likesDao.deletePostLike(user_id, post_id);
            return false;
        }
    }

    public boolean isPostLikedByUser(int post_id, int user_id) {
        return likesDao.likePost(user_id, post_id);
    }

    @Override
    public int countPostLikes(int post_id) {
        return likesDao.countPostLike(post_id);
    }

}
