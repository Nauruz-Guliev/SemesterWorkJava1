package ru.kpfu.itis.gnt.controllers.pages;

import ru.kpfu.itis.gnt.DAO.implementations.UsersRepositoryJDBCTemplateImpl;
import ru.kpfu.itis.gnt.constants.ListenerConstants;
import ru.kpfu.itis.gnt.entities.User;
import ru.kpfu.itis.gnt.exceptions.DBException;
import ru.kpfu.itis.gnt.services.implementations.UsersAuthenticationService;
import ru.kpfu.itis.gnt.services.implementations.UsersServiceImpl;

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

    private UsersAuthenticationService usersService;

    @Override
    public void init() {
        usersService = new UsersAuthenticationService(
                (UsersRepositoryJDBCTemplateImpl) getServletContext().getAttribute(ListenerConstants.KEY_USER_DAO)
        );
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        try {
            updateTable(request);
        } catch (DBException ex) {

        }
        getServletContext().getRequestDispatcher("/WEB-INF/views/admin_page.jsp").forward(request, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("userId"));
            String newCountry = request.getParameter("newCountry");
            usersService.updateCountry(newCountry, id);
            updateTable(request);
        } catch (NumberFormatException ex) {
            request.setAttribute("updateErrorMessage", "Wrong input format!");
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/views/admin_page.jsp").forward(request, resp);
    }

    private void updateTable(HttpServletRequest request) throws DBException{
        List<User> users = usersService.findAll();
        request.setAttribute("usersList", users);
    }
}