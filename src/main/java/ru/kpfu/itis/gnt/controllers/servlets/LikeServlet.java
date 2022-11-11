package ru.kpfu.itis.gnt.controllers.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.kpfu.itis.gnt.DAO.implementations.*;
import ru.kpfu.itis.gnt.Utils.RedirectHelper;
import ru.kpfu.itis.gnt.constants.FieldsConstants;
import ru.kpfu.itis.gnt.constants.ListenerConstants;
import ru.kpfu.itis.gnt.services.implementations.CommentsServiceImpl;
import ru.kpfu.itis.gnt.services.implementations.LikesServiceImpl;
import ru.kpfu.itis.gnt.services.implementations.PostsServiceImpl;
import ru.kpfu.itis.gnt.services.implementations.UsersService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/post/like")
public class LikeServlet extends HttpServlet {

    private PostsServiceImpl postsService;
    private CommentsServiceImpl commentsService;
    private UsersService usersService;
    private LikesServiceImpl likesService;



    private int postId;
    private int postLikeCount;
    private int userId;

    @Override
    public void init() {
        PostsRepositoryImpl postsDao = (PostsRepositoryImpl) getServletContext().getAttribute(ListenerConstants.KEY_POSTS_DAO);
        UsersRepositoryJDBCTemplateImpl usersDao = (UsersRepositoryJDBCTemplateImpl) getServletContext().getAttribute(ListenerConstants.KEY_USER_DAO);
        CommentsRepositoryImpl commentsDao = (CommentsRepositoryImpl) getServletContext().getAttribute(ListenerConstants.KEY_COMMENTS_DAO);
        LikesRepositoryImpl likesDao = (LikesRepositoryImpl) getServletContext().getAttribute(ListenerConstants.KEY_LIKES_DAO);
        TagsRepositoryImpl tagsDao = (TagsRepositoryImpl) getServletContext().getAttribute(ListenerConstants.KEY_TAGS_DAO);
        TagNamesRepositoryImpl tagNamesDao= (TagNamesRepositoryImpl) getServletContext().getAttribute(ListenerConstants.KEY_TAG_NAME_DAO);

        likesService = new LikesServiceImpl(usersDao, likesDao, postsDao, commentsDao);
        postsService = new PostsServiceImpl(postsDao, usersDao, tagsDao, tagNamesDao);
        commentsService = new CommentsServiceImpl(postsDao, usersDao, commentsDao);
        usersService = new UsersService(usersDao);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            postLikeCount = likesService.countPostLikes(postId);

            // req.setAttribute(FieldsConstants.LIKE_COUNT_ATTRIBUTE, postLikeCount);

            likePost(resp, (ObjectMapper) getServletContext().getAttribute(ListenerConstants.KEY_OBJECT_MAPPER), req);

            // getServletContext().getRequestDispatcher("/WEB-INF/views/post.jsp").forward(req, resp);
        } catch (Exception ex) {
            RedirectHelper.forwardWithMessage(req, resp, "main",  ex.getMessage(), ex.getClass().getName());
        }
    }

    private void likePost(HttpServletResponse resp, ObjectMapper objectMapper, HttpServletRequest req) throws IOException {
        initValues(req);
        likesService.likePost(postId, userId);
        postLikeCount = likesService.countPostLikes(postId);
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
