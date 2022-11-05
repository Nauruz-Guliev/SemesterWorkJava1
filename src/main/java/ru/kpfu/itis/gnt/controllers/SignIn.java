package ru.kpfu.itis.gnt.controllers;



import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.kpfu.itis.gnt.DAO.DataSource;
import ru.kpfu.itis.gnt.DAO.UsersRepositoryJDBCTemplateImpl;
import ru.kpfu.itis.gnt.Utils.Encrypter;
import ru.kpfu.itis.gnt.exceptions.DBException;
import ru.kpfu.itis.gnt.services.SecurityService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/signin")
public class SignIn extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/signin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            SecurityService securityService = new SecurityService(getServletContext());
            if(email != null && password != null){
                if(securityService.signIn(req, email, Encrypter.md5Hex(password))){
                    resp.sendRedirect(getServletContext().getContextPath() + "/profile");
                    return;
                }
            }
        } catch (DBException e) {
            throw new RuntimeException(e.getMessage());
        }
        req.setAttribute("errorMessage", "Wrong email or password");
        req.setAttribute("email", req.getParameter("email"));
        getServletContext().getRequestDispatcher("/WEB-INF/views/signin.jsp").forward(req, resp);
    }

}
