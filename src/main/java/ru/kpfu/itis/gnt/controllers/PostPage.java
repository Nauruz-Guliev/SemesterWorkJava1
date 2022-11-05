package ru.kpfu.itis.gnt.controllers;

import ru.kpfu.itis.gnt.entities.Post;
import ru.kpfu.itis.gnt.exceptions.DBException;
import ru.kpfu.itis.gnt.services.PostsServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/article")

public class PostPage extends HttpServlet {
    private Post post;
    private int postId;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        postId = Integer.parseInt(req.getParameter("postIndex"));
        initArticle();
        req.setAttribute("post", post);
        getServletContext().getRequestDispatcher("/WEB-INF/views/article.jsp").forward(req, resp);
    }

    private void initArticle(){
        PostsServiceImpl postsService = new PostsServiceImpl(getServletContext());
        try {
            post = postsService.getPostById(postId);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
    }
}
