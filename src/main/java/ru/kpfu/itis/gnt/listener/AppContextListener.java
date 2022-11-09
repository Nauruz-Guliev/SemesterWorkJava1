package ru.kpfu.itis.gnt.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.kpfu.itis.gnt.DAO.implementations.*;
import ru.kpfu.itis.gnt.constants.ListenerConstants;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class AppContextListener implements ServletContextListener {

    private DriverManagerDataSource dataSource;
    private UsersRepositoryJDBCTemplateImpl userDAO;

    private PostsRepositoryImpl postDao;

    private CommentsRepositoryImpl commentsDao;
    private LikesRepositoryImpl likesDao;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        dataSource = new DataSource().getDataSource();
        userDAO = new UsersRepositoryJDBCTemplateImpl(dataSource);
        postDao = new PostsRepositoryImpl(dataSource);
        commentsDao = new CommentsRepositoryImpl(dataSource);
        likesDao = new LikesRepositoryImpl(dataSource);

        servletContextEvent.getServletContext().setAttribute(ListenerConstants.KEY_LIKES_DAO, likesDao);
        servletContextEvent.getServletContext().setAttribute(ListenerConstants.KEY_POSTS_DAO, postDao);
        servletContextEvent.getServletContext().setAttribute(ListenerConstants.KEY_COMMENTS_DAO, commentsDao);
        servletContextEvent.getServletContext().setAttribute(ListenerConstants.KEY_USER_DAO, userDAO);
        servletContextEvent.getServletContext().setAttribute(ListenerConstants.KEY_DATA_SOURCE, dataSource);
        servletContextEvent.getServletContext().setAttribute(ListenerConstants.KEY_OBJECT_MAPPER, new ObjectMapper());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        try {
            dataSource.getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
