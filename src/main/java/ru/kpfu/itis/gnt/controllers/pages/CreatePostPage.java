package ru.kpfu.itis.gnt.controllers.pages;


import ru.kpfu.itis.gnt.DAO.implementations.PostsRepositoryImpl;
import ru.kpfu.itis.gnt.DAO.implementations.TagNamesRepositoryImpl;
import ru.kpfu.itis.gnt.DAO.implementations.TagsRepositoryImpl;
import ru.kpfu.itis.gnt.DAO.implementations.UsersRepositoryJDBCTemplateImpl;
import ru.kpfu.itis.gnt.Utils.RedirectHelper;
import ru.kpfu.itis.gnt.constants.ListenerConstants;
import ru.kpfu.itis.gnt.constants.PagePathConstants;
import ru.kpfu.itis.gnt.entities.Post;
import ru.kpfu.itis.gnt.exceptions.DBException;
import ru.kpfu.itis.gnt.services.implementations.PostsServiceImpl;
import ru.kpfu.itis.gnt.validators.ClassNameGetter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/post/create")
public class CreatePostPage extends HttpServlet {

    private ArrayList<String> postTags;
    private String postTitle;
    private String postBody;

    private PostsRepositoryImpl postsDao;
    private UsersRepositoryJDBCTemplateImpl usersDao;

    private PostsServiceImpl postsService;
    private TagsRepositoryImpl tagsDao;
    private TagNamesRepositoryImpl tagNamesDao;


    private String successMessage = "Post has been created successfully!";
    private String errorMessage = "Couldn't add post. ";


    @Override
    public void init() throws ServletException {
        postTags = new ArrayList<>();
        postsDao = (PostsRepositoryImpl) getServletContext().getAttribute(ListenerConstants.KEY_POSTS_DAO);
        usersDao = (UsersRepositoryJDBCTemplateImpl) getServletContext().getAttribute(ListenerConstants.KEY_USER_DAO);
        tagsDao = (TagsRepositoryImpl) getServletContext().getAttribute(ListenerConstants.KEY_TAGS_DAO);
        tagNamesDao = (TagNamesRepositoryImpl) getServletContext().getAttribute(ListenerConstants.KEY_TAG_NAME_DAO);

        postsService = new PostsServiceImpl(postsDao, usersDao, tagsDao, tagNamesDao);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(PagePathConstants.CREATE_POST_PAGE_PATH).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            postTags.add(req.getParameter("tag"));
            for (int i = 0; i < 5; i++) {
                String tag = req.getParameter("tag" + i);
                if (tag != null && tag.length() > 2) {
                    postTags.add(tag);
                }
            }

            System.out.println("TAGS "+ postTags);
            initValues(req);
            Post post = new Post(postTitle, postBody, (Integer) req.getSession().getAttribute("USER_ID"));
            if (postsService.addPost(post, postTags)) {
                req.setAttribute("popupMessageBody", "success");
                req.setAttribute("popupMessageTitle", "success");
                resp.sendRedirect(req.getContextPath() + "/main");
            } else {
                RedirectHelper.forwardWithMessage(req, resp, "create_post", errorMessage + "Some fields were empty", "Error");
            }
        } catch (DBException ex) {
            RedirectHelper.forwardWithMessage(req, resp, "main", errorMessage + ex, ClassNameGetter.getClassName(ex.getClass().getName()));
        }
    }

    private void initValues(HttpServletRequest req) {
        postTitle = req.getParameter("title");
        postBody = req.getParameter("body");
    }
}
