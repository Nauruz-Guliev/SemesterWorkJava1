package ru.kpfu.itis.gnt.controllers.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.kpfu.itis.gnt.DAO.implementations.CommentsRepositoryImpl;
import ru.kpfu.itis.gnt.DAO.implementations.LikesRepositoryImpl;
import ru.kpfu.itis.gnt.DAO.implementations.PostsRepositoryImpl;
import ru.kpfu.itis.gnt.DAO.implementations.UsersRepositoryJDBCTemplateImpl;
import ru.kpfu.itis.gnt.constants.FieldsConstants;
import ru.kpfu.itis.gnt.constants.ListenerConstants;
import ru.kpfu.itis.gnt.services.implementations.CommentsServiceImpl;
import ru.kpfu.itis.gnt.services.implementations.LikesServiceImpl;
import ru.kpfu.itis.gnt.services.implementations.PostsServiceImpl;
import ru.kpfu.itis.gnt.services.implementations.UsersAuthenticationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/article/like")
public class LikeServlet extends HttpServlet {

    private PostsServiceImpl postsService;
    private CommentsServiceImpl commentsService;
    private UsersAuthenticationService usersService;
    private LikesServiceImpl likesService;

    private PostsRepositoryImpl postsDao;
    private UsersRepositoryJDBCTemplateImpl usersDao;

    private LikesRepositoryImpl likesDao;
    private CommentsRepositoryImpl commentsDao;


    private int postId;
    private int postLikeCount;
    private int userId;

    @Override
    public void init() {
        postsDao = (PostsRepositoryImpl) getServletContext().getAttribute(ListenerConstants.KEY_POSTS_DAO);
        usersDao = (UsersRepositoryJDBCTemplateImpl) getServletContext().getAttribute(ListenerConstants.KEY_USER_DAO);
        commentsDao = (CommentsRepositoryImpl) getServletContext().getAttribute(ListenerConstants.KEY_COMMENTS_DAO);
        likesDao = (LikesRepositoryImpl) getServletContext().getAttribute(ListenerConstants.KEY_LIKES_DAO);

        likesService = new LikesServiceImpl(usersDao, likesDao, postsDao, commentsDao);
        postsService = new PostsServiceImpl(postsDao, usersDao);
        commentsService = new CommentsServiceImpl(postsDao, usersDao, commentsDao);
        usersService = new UsersAuthenticationService(usersDao);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            postLikeCount = likesService.countPostLikes(postId);

            // req.setAttribute(FieldsConstants.LIKE_COUNT_ATTRIBUTE, postLikeCount);

            likePost(resp, (ObjectMapper) getServletContext().getAttribute(ListenerConstants.KEY_OBJECT_MAPPER), req);

            // getServletContext().getRequestDispatcher("/WEB-INF/views/article.jsp").forward(req, resp);
        } catch (Exception ex) {
            System.out.println("asdasd" + ex);
        }
    }

    private void likePost(HttpServletResponse resp, ObjectMapper objectMapper, HttpServletRequest req) throws IOException {
        initValues(req);
        System.out.println(postId + " " + userId);
        likesService.likePost(postId, userId);
        postLikeCount = likesService.countPostLikes(postId);
        System.out.println(postId + " " + postLikeCount);
        String jsonResponse = objectMapper.writeValueAsString(postLikeCount);
        resp.setContentType("application/json");
        resp.getWriter().write(jsonResponse);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(getServletContext().getContextPath() + "/");
    }

    private void initValues(HttpServletRequest req) {
        postId = Integer.parseInt(req.getParameter("postIndex"));
        userId = (int) req.getSession().getAttribute(FieldsConstants.USER_ID_ATTRIBUTE);

        postLikeCount = likesService.countPostLikes(postId);
    }
}
