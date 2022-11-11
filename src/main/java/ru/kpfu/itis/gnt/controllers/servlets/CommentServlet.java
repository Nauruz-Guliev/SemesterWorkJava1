package ru.kpfu.itis.gnt.controllers.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.kpfu.itis.gnt.DAO.implementations.*;
import ru.kpfu.itis.gnt.Utils.RedirectHelper;
import ru.kpfu.itis.gnt.constants.ListenerConstants;
import ru.kpfu.itis.gnt.entities.Comment;
import ru.kpfu.itis.gnt.entities.CommentObject;
import ru.kpfu.itis.gnt.entities.Post;
import ru.kpfu.itis.gnt.entities.User;
import ru.kpfu.itis.gnt.exceptions.DBException;
import ru.kpfu.itis.gnt.services.implementations.CommentsServiceImpl;
import ru.kpfu.itis.gnt.services.implementations.PostsServiceImpl;
import ru.kpfu.itis.gnt.services.implementations.UsersService;

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

    private UsersService usersService;

    private List<ru.kpfu.itis.gnt.entities.Comment> commentList;

    private User postAuthor;

    private User commentAuthor;
    private Post post;
    private int commentAuthorId;


    @Override
    public void init() {

        PostsRepositoryImpl  postsDao = (PostsRepositoryImpl) getServletContext().getAttribute(ListenerConstants.KEY_POSTS_DAO);
        UsersRepositoryJDBCTemplateImpl usersDao = (UsersRepositoryJDBCTemplateImpl) getServletContext().getAttribute(ListenerConstants.KEY_USER_DAO);
        CommentsRepositoryImpl commentsDao = (CommentsRepositoryImpl) getServletContext().getAttribute(ListenerConstants.KEY_COMMENTS_DAO);
        TagsRepositoryImpl tagsDao = (TagsRepositoryImpl) getServletContext().getAttribute(ListenerConstants.KEY_TAGS_DAO);
        TagNamesRepositoryImpl tagNamesDao= (TagNamesRepositoryImpl) getServletContext().getAttribute(ListenerConstants.KEY_TAG_NAME_DAO);

        postsService = new PostsServiceImpl(postsDao, usersDao, tagsDao, tagNamesDao);
        commentsService = new CommentsServiceImpl(postsDao, usersDao, commentsDao);
        usersService = new UsersService(usersDao);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            initValues(req);
            addCommentToDB(resp, (ObjectMapper) getServletContext().getAttribute(ListenerConstants.KEY_OBJECT_MAPPER), req);
        } catch (NumberFormatException | DBException | IOException | ServletException e) {
            throw new RuntimeException(e);
        }
    }

    private void addCommentToDB(HttpServletResponse resp, ObjectMapper objectMapper, HttpServletRequest req) throws IOException, ServletException {

        try {
            Comment commentServletToDb = new Comment(
                    commentBody,
                    postId,
                    commentAuthorId
            );

            // если комментарий успешно добавился в бд,
            // то тогда создаём новый объект комментария и передаем на фронт
            // с помощью json
            if (commentsService.addComment(commentServletToDb)) {
                int commentsCount = commentsService.getCommentsCount(postId);
                commentList = commentsService.getComments(post, 1, commentsCount-1);
                // отображаем только последний
                Comment comment = commentList.get(commentList.size() - 1);
                commentAuthor = usersService.findUserById(comment.getAuthor_id());

                CommentObject commentToDisplay = new CommentObject(
                        commentBody,
                        postId,
                        commentAuthor.getFirstName() + " " + commentAuthor.getLastName(),
                        comment.getCreated_at()
                );
                String jsonResponse = objectMapper.writeValueAsString(commentToDisplay);
                resp.setContentType("application/json");
                resp.getWriter().write(jsonResponse);
            }
        } catch (IOException | DBException | NumberFormatException ex) {
            RedirectHelper.forwardWithMessage(req, resp, "main",  ex.getMessage(), ex.getClass().getName());
        }
    }


    private void initValues(HttpServletRequest req) throws DBException, NumberFormatException {
        postId = Integer.parseInt(req.getParameter("postIndex"));
        commentBody = req.getParameter("comment");
        post = postsService.getPostById(postId);
        postAuthor = postsService.getPostAuthor(post);
        commentAuthorId = (int) req.getSession().getAttribute("USER_ID");
    }
}
