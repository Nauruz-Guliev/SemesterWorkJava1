package ru.kpfu.itis.gnt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.kpfu.itis.gnt.entities.Comment;
import ru.kpfu.itis.gnt.entities.Post;
import ru.kpfu.itis.gnt.entities.User;
import ru.kpfu.itis.gnt.exceptions.DBException;
import ru.kpfu.itis.gnt.services.CommentsServiceImpl;
import ru.kpfu.itis.gnt.services.PostsServiceImpl;
import ru.kpfu.itis.gnt.services.UsersAuthenticationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/article")

public class PostPage extends HttpServlet {
    private Post post;
    private int postId;

    private List<Comment> commentList;
    private User postAuthor;

    private List<User> commentAuthors;

    private PostsServiceImpl postsService;
    private CommentsServiceImpl commentsService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            postId = Integer.parseInt(req.getParameter("postIndex"));
            initValues();
            req.setAttribute("post", post);
            req.setAttribute("postAuthor", postAuthor);
            req.setAttribute("commentList", commentList);
            req.setAttribute("commentAuthors", commentAuthors);
            initValues();

        } catch (NumberFormatException ex) {

        }
        getServletContext().getRequestDispatcher("/WEB-INF/views/article.jsp").forward(req, resp);
    }



    private void initValues() {
        postsService = new PostsServiceImpl(getServletContext());
        commentsService = new CommentsServiceImpl(getServletContext());
        try {
            post = postsService.getPostById(postId);
            postAuthor = postsService.getPostAuthor(post);
            commentList = commentsService.getAllComments(post);
            commentAuthors = commentsService.getCommentAuthors(commentList);
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
    }

}
