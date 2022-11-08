package ru.kpfu.itis.gnt.controllers.pages;

import ru.kpfu.itis.gnt.DAO.implementations.CommentsRepositoryImpl;
import ru.kpfu.itis.gnt.DAO.implementations.PostsRepositoryImpl;
import ru.kpfu.itis.gnt.DAO.implementations.UsersRepositoryJDBCTemplateImpl;
import ru.kpfu.itis.gnt.constants.ListenerConstants;
import ru.kpfu.itis.gnt.entities.Comment;
import ru.kpfu.itis.gnt.entities.Post;
import ru.kpfu.itis.gnt.entities.User;
import ru.kpfu.itis.gnt.exceptions.DBException;
import ru.kpfu.itis.gnt.services.implementations.CommentsServiceImpl;
import ru.kpfu.itis.gnt.services.implementations.PostsServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@WebServlet("/article")

public class PostPage extends HttpServlet {
    private Post post;
    private int postId;

    private List<Comment> commentList;
    private User postAuthor;

    private HashMap<Comment, User> commentAuthors;

    private PostsServiceImpl postsService;
    private CommentsServiceImpl commentsService;

    private  PostsRepositoryImpl postsDao;
    private  UsersRepositoryJDBCTemplateImpl usersDao;
    private  CommentsRepositoryImpl commentsDao;


    @Override
    public void init() {
        postsDao = (PostsRepositoryImpl) getServletContext().getAttribute(ListenerConstants.KEY_POSTS_DAO);
        usersDao = (UsersRepositoryJDBCTemplateImpl) getServletContext().getAttribute(ListenerConstants.KEY_USER_DAO);
        commentsDao = (CommentsRepositoryImpl) getServletContext().getAttribute(ListenerConstants.KEY_COMMENTS_DAO);

        postsService = new PostsServiceImpl(postsDao, usersDao);
        commentsService = new CommentsServiceImpl(postsDao, usersDao, commentsDao);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            postId = Integer.parseInt(req.getParameter("postIndex"));
            initValues();
            req.setAttribute("post", post);
            req.setAttribute("postAuthor", postAuthor);
            req.setAttribute("commentList", commentList);
            req.setAttribute("commentAuthors", commentAuthors);

            getServletContext().getRequestDispatcher("/WEB-INF/views/article.jsp").forward(req, resp);
        } catch (ServletException | IOException | NumberFormatException | DBException ex) {
            getServletContext().getRequestDispatcher("/WEB-INF/views/article.jsp").forward(req, resp);
        }
    }


    private void initValues() throws DBException {
        post = postsService.getPostById(postId);
        postAuthor = postsService.getPostAuthor(post);
        commentList = commentsService.getAllComments(post);
        commentAuthors = commentsService.getCommentAuthors(commentList);
    }

}
