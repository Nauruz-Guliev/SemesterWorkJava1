package ru.kpfu.itis.gnt.controllers.pages;


import ru.kpfu.itis.gnt.DAO.implementations.PostsRepositoryImpl;
import ru.kpfu.itis.gnt.DAO.implementations.UsersRepositoryJDBCTemplateImpl;
import ru.kpfu.itis.gnt.Utils.CookieMessageAdder;
import ru.kpfu.itis.gnt.constants.CookieConstants;
import ru.kpfu.itis.gnt.constants.ListenerConstants;
import ru.kpfu.itis.gnt.entities.Post;
import ru.kpfu.itis.gnt.exceptions.DBException;
import ru.kpfu.itis.gnt.services.implementations.PostsServiceImpl;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/main")
public class Main extends HttpServlet {
    private List<Post> postList;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getPosts(10, 0, resp);
        req.setAttribute("email", req.getSession().getAttribute("email"));
        req.setAttribute("postsList", postList);
        getServletContext().getRequestDispatcher("/WEB-INF/views/main.jsp").forward(req, resp);
    }


    private void getPosts(int limit, int offset, HttpServletResponse response) {
        PostsServiceImpl postsService = new PostsServiceImpl(
                (PostsRepositoryImpl) getServletContext().getAttribute(ListenerConstants.KEY_POSTS_DAO),
                (UsersRepositoryJDBCTemplateImpl) getServletContext().getAttribute(ListenerConstants.KEY_USER_DAO)
        );
        try {
            postList = postsService.getPosts(limit, offset);
        } catch (DBException e) {
            CookieMessageAdder.addMessage(response, CookieConstants.ERROR_MESSAGE, e.getMessage());
        }
    }
}
