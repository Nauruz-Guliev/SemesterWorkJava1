package ru.kpfu.itis.gnt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.kpfu.itis.gnt.entities.Comment;
import ru.kpfu.itis.gnt.entities.CommentObject;
import ru.kpfu.itis.gnt.entities.Post;
import ru.kpfu.itis.gnt.entities.User;
import ru.kpfu.itis.gnt.exceptions.DBException;
import ru.kpfu.itis.gnt.services.CommentsServiceImpl;
import ru.kpfu.itis.gnt.services.PostsServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/comment")
public class CommentServlet extends HttpServlet {
    private int postId;


    private String commentBody;
    private PostsServiceImpl postsService;
    private CommentsServiceImpl commentsService;

    private List<ru.kpfu.itis.gnt.entities.Comment> commentList;
    private List<User> commentAuthors;

    private User postAuthor;
    private Post post;

    @Override
    public void init() {
        postsService = new PostsServiceImpl(getServletContext());
        commentsService = new CommentsServiceImpl(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            initValues(req);
            ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute("OBJECT_MAPPER");
            addCommentToDB(resp, objectMapper);
        } catch (NumberFormatException | IOException | DBException e) {
            throw new RuntimeException(e);
        }
    }

    private void addCommentToDB(HttpServletResponse resp, ObjectMapper objectMapper) throws DBException, IOException {
        Comment commentServletToDb = new Comment(
                commentBody,
                postId,
                postAuthor.getId()
        );
        // если комментарий успешно добавился в бд,
        // то тогда создаём новый объект комментария и передаем на фронт
        // с помощью json
        if(commentsService.addComment(commentServletToDb)) {
            commentsService.addComment(commentServletToDb);
            commentList = commentsService.getAllComments(post);
            CommentObject commentToDisplay = new CommentObject(
                    commentBody,
                    postId,
                    postAuthor.getLastName() + " " + postAuthor.getFirstName(),
                    commentList.get(commentList.size() - 1).getCreated_at()
            );
            String jsonResponse = objectMapper.writeValueAsString(commentToDisplay);
            resp.setContentType("application/json");
            resp.getWriter().write(jsonResponse);
        }
    }


    private void initValues(HttpServletRequest req) throws DBException, NumberFormatException {
        postId = Integer.parseInt(req.getParameter("postIndex"));
        commentBody = req.getParameter("comment");
        post = postsService.getPostById(postId);
        postAuthor = postsService.getPostAuthor(post);
        commentList = commentsService.getAllComments(post);
        commentAuthors = commentsService.getCommentAuthors(commentList);
    }
}
