package ru.kpfu.itis.gnt.services.implementations;

import ru.kpfu.itis.gnt.DAO.implementations.CommentsRepositoryImpl;
import ru.kpfu.itis.gnt.DAO.implementations.LikesRepositoryImpl;
import ru.kpfu.itis.gnt.DAO.implementations.PostsRepositoryImpl;
import ru.kpfu.itis.gnt.DAO.implementations.UsersRepositoryJDBCTemplateImpl;
import ru.kpfu.itis.gnt.services.LikesService;

import javax.servlet.annotation.WebServlet;



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
    public boolean likeComment(int comment_id, int user_id) {
        return false;
    }

    @Override
    public boolean likePost(int post_id, int user_id) {
        if (!likesDao.findPostLike(user_id, post_id)) {
            return likesDao.likePost(user_id, post_id);
        } else {
            likesDao.deletePostLike(user_id, post_id);
            return false;
        }
    }

    @Override
    public int countPostLikes(int post_id) {
        return likesDao.countPostLike(post_id);
    }

}
