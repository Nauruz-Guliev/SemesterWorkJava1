package ru.kpfu.itis.gnt.controllers.pages;


import ru.kpfu.itis.gnt.DAO.TagNamesRepository;
import ru.kpfu.itis.gnt.DAO.implementations.PostsRepositoryImpl;
import ru.kpfu.itis.gnt.DAO.implementations.TagsRepositoryImpl;
import ru.kpfu.itis.gnt.DAO.implementations.UsersRepositoryJDBCTemplateImpl;
import ru.kpfu.itis.gnt.Utils.RedirectHelper;
import ru.kpfu.itis.gnt.constants.ListenerConstants;
import ru.kpfu.itis.gnt.entities.Post;
import ru.kpfu.itis.gnt.exceptions.DBException;
import ru.kpfu.itis.gnt.services.implementations.PostsServiceImpl;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/main")
public class MainPage extends HttpServlet {
    private List<Post> postList;
    private List<Post> mostPopular;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            getPosts(10, 0, resp);
            req.setAttribute("email", req.getSession().getAttribute("email"));
            req.setAttribute("postsList", postList);
            req.setAttribute("mostPopular", mostPopular);

            getServletContext().getRequestDispatcher("/WEB-INF/views/main.jsp").forward(req, resp);
        } catch (DBException e) {
            RedirectHelper.forwardWithMessage(req, resp, "/main",  e.getMessage(), e.getClass().getName());
        }
    }


    private void getPosts(int limit, int offset, HttpServletResponse response) throws DBException {
        PostsServiceImpl postsService = new PostsServiceImpl(
                (PostsRepositoryImpl) getServletContext().getAttribute(ListenerConstants.KEY_POSTS_DAO),
                (UsersRepositoryJDBCTemplateImpl) getServletContext().getAttribute(ListenerConstants.KEY_USER_DAO),
                (TagsRepositoryImpl) getServletContext().getAttribute(ListenerConstants.KEY_TAGS_DAO),
                (TagNamesRepository) getServletContext().getAttribute(ListenerConstants.KEY_TAG_NAME_DAO));

        postList = postsService.getPosts(limit, offset);
        mostPopular = postsService.getMostPopularPosts();



    }
}
