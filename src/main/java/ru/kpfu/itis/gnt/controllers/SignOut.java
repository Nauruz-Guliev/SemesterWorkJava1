package ru.kpfu.itis.gnt.controllers;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.kpfu.itis.gnt.DAO.DataSource;
import ru.kpfu.itis.gnt.DAO.UsersRepositoryJDBCTemplateImpl;
import ru.kpfu.itis.gnt.exceptions.DBException;
import ru.kpfu.itis.gnt.services.SecurityService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/signout")
public class SignOut extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            SecurityService securityService = new SecurityService(getServletContext());
            securityService.signOut(req);
            resp.sendRedirect(getServletContext().getContextPath() + "/");
        } catch (DBException e) {
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  {

    }
}
