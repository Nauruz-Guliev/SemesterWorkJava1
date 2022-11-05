package ru.kpfu.itis.gnt.controllers;

import ru.kpfu.itis.gnt.DAO.UsersRepositoryJDBCTemplateImpl;
import ru.kpfu.itis.gnt.entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/admin")
public class AdminPage extends HttpServlet {

    private UsersRepositoryJDBCTemplateImpl userDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        userDAO = (UsersRepositoryJDBCTemplateImpl) getServletContext().getAttribute("USERDAO");
        updateTable(request);
        getServletContext().getRequestDispatcher("/WEB-INF/views/admin_page.jsp").forward(request, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        userDAO = (UsersRepositoryJDBCTemplateImpl) getServletContext().getAttribute("USERDAO");

        try {
            int id = Integer.parseInt(request.getParameter("userId"));
            String newCountry = request.getParameter("newCountry");
            if (!userDAO.updateCountry(newCountry, id)) {
                request.setAttribute("updateErrorMessage", "No user with such an ID");
            }
        } catch (NumberFormatException ex) {
            request.setAttribute("updateErrorMessage", "Wrong input format!");
        }

        updateTable(request);
        getServletContext().getRequestDispatcher("/WEB-INF/views/admin_page.jsp").forward(request, resp);
    }

    private void updateTable(HttpServletRequest request) {
        Optional<List<User>> users = userDAO.findAll();
        users.ifPresent(userList -> request.setAttribute("usersList", userList));
    }
}
