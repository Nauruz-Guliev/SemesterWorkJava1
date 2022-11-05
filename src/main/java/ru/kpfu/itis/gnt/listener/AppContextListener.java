package ru.kpfu.itis.gnt.listener;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.kpfu.itis.gnt.DAO.DataSource;
import ru.kpfu.itis.gnt.DAO.UsersRepositoryJDBCTemplateImpl;
import ru.kpfu.itis.gnt.exceptions.DBException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.SQLException;

@WebListener
public class AppContextListener implements ServletContextListener {

    private DriverManagerDataSource dataSource;
    private UsersRepositoryJDBCTemplateImpl userDAO;
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        dataSource = new DataSource().getDataSource();
        userDAO = new UsersRepositoryJDBCTemplateImpl(dataSource);

        servletContextEvent.getServletContext().setAttribute("USERDAO", userDAO);
        servletContextEvent.getServletContext().setAttribute("CONNECTION", dataSource);
    }
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
