package ru.kpfu.itis.gnt.controllers.pages;

import ru.kpfu.itis.gnt.DAO.implementations.*;
import ru.kpfu.itis.gnt.Utils.RedirectHelper;
import ru.kpfu.itis.gnt.constants.FieldsConstants;
import ru.kpfu.itis.gnt.constants.ListenerConstants;
import ru.kpfu.itis.gnt.entities.Comment;
import ru.kpfu.itis.gnt.entities.Post;
import ru.kpfu.itis.gnt.entities.Tag;
import ru.kpfu.itis.gnt.entities.User;
import ru.kpfu.itis.gnt.exceptions.DBException;
import ru.kpfu.itis.gnt.services.implementations.*;
import ru.kpfu.itis.gnt.Utils.validators.ClassNameGetter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

@WebServlet("/post")

public class PostPage extends HttpServlet {
    private Post post;
    private int postId;
    private boolean isLiked;

    private List<Comment> commentList;
    private User postAuthor;
    private int likeCount;

    private HashMap<Comment, User> commentAuthors;

    private PostsServiceImpl postsService;
    private CommentsServiceImpl commentsService;

    private LikesServiceImpl likesService;
    private UsersService usersService;

    private TagsServiceImpl tagsService;

    private List<Tag> postTags;

    private int userId;


    private int commentCount;
    private int commentOffset;


    @Override
    public void init() {
        PostsRepositoryImpl postsDao = (PostsRepositoryImpl) getServletContext().getAttribute(ListenerConstants.KEY_POSTS_DAO);
        UsersRepositoryJDBCTemplateImpl usersDao = (UsersRepositoryJDBCTemplateImpl) getServletContext().getAttribute(ListenerConstants.KEY_USER_DAO);
        CommentsRepositoryImpl commentsDao = (CommentsRepositoryImpl) getServletContext().getAttribute(ListenerConstants.KEY_COMMENTS_DAO);
        LikesRepositoryImpl likesDao = (LikesRepositoryImpl) getServletContext().getAttribute(ListenerConstants.KEY_LIKES_DAO);
        TagsRepositoryImpl tagsDao = (TagsRepositoryImpl) getServletContext().getAttribute(ListenerConstants.KEY_TAGS_DAO);
        TagNamesRepositoryImpl tagNamesDao = (TagNamesRepositoryImpl) getServletContext().getAttribute(ListenerConstants.KEY_TAG_NAME_DAO);

        tagsService = new TagsServiceImpl(tagsDao, tagNamesDao);
        usersService = new UsersService(usersDao);
        postsService = new PostsServiceImpl(postsDao, usersDao, tagsDao, tagNamesDao);
        commentsService = new CommentsServiceImpl(postsDao, usersDao, commentsDao);
        likesService = new LikesServiceImpl(usersDao, likesDao, postsDao, commentsDao);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.getSession().invalidate();
            String offset = req.getParameter("offset");
            if (offset != null) {
                commentOffset = Integer.parseInt(offset);
            } else {
                commentOffset = 0;
            }
            postId = Integer.parseInt(req.getParameter("postIndex"));
            if (usersService.isSigned(req)) {
                userId = (int) req.getSession().getAttribute(FieldsConstants.USER_ID_ATTRIBUTE);
            }
            isLiked = likesService.isPostLikedByUser(postId, userId);
            likeCount = likesService.countPostLikes(postId);
            initValues();
            System.out.println("asdasd" + postTags);
            setAttributes(req);

            getServletContext().getRequestDispatcher("/WEB-INF/views/post.jsp").forward(req, resp);
        } catch (ParseException | ServletException | IOException | DBException ex) {
            //  RedirectHelper.redirect(req, resp, "/post", ex.getMessage());
             RedirectHelper.forwardWithMessage(req, resp, "main",  ex.getMessage(), ClassNameGetter.getClassName(ex.getClass().getName()));
        } catch (NumberFormatException ex) {
            resp.sendRedirect("/main");
        }
    }

    private void initValues() throws DBException, ParseException {
        post = postsService.getPostById(postId);
        postAuthor = postsService.getPostAuthor(post);
        commentList = commentsService.getComments(post, 10, commentOffset);
        commentCount = commentsService.getCommentsCount(postId);
        commentAuthors = commentsService.getCommentAuthors(commentList);
        postTags = tagsService.findAllTags(postId);
    }

    private void setAttributes(HttpServletRequest req) {
        req.setAttribute("postTags", postTags);
        req.setAttribute("isLiked", isLiked);
        req.setAttribute("post", post);
        req.setAttribute("postAuthor", postAuthor);
        req.setAttribute("commentList", commentList);
        req.setAttribute("commentAuthors", commentAuthors);
        req.setAttribute("userId", userId);
        req.setAttribute("likeCount", likeCount);
        req.setAttribute("commentCount", commentCount);
    }

}
