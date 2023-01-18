package ru.kpfu.itis.gnt.controllers.pages;

import ru.kpfu.itis.gnt.DAO.implementations.UsersRepositoryJDBCTemplateImpl;
import ru.kpfu.itis.gnt.Utils.RedirectHelper;
import ru.kpfu.itis.gnt.constants.ListenerConstants;
import ru.kpfu.itis.gnt.constants.PagePathConstants;
import ru.kpfu.itis.gnt.entities.User;
import ru.kpfu.itis.gnt.exceptions.DBException;
import ru.kpfu.itis.gnt.services.implementations.UsersService;
import ru.kpfu.itis.gnt.validators.ClassNameGetter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin")
public class AdminPage extends HttpServlet {
    private UsersService usersService;

    @Override
    public void init() {
        usersService = new UsersService(
                (UsersRepositoryJDBCTemplateImpl) getServletContext().getAttribute(ListenerConstants.KEY_USER_DAO)
        );
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        try {
            updateTable(request);
        } catch (DBException ex) {

            RedirectHelper.forwardWithMessage(request, resp, "admin", ex.getMessage(), ClassNameGetter.getClassName(ex.getClass().getName()));
        }
        getServletContext().getRequestDispatcher(PagePathConstants.ADMIN_PAGE_PATH).forward(request, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("userId"));
            String newCountry = request.getParameter("country");
            usersService.updateCountry(newCountry, id);
            updateTable(request);
            RedirectHelper.forwardWithMessage(request, resp, "admin_page", "Country was changed!", "Success");
        } catch (NumberFormatException | DBException ex) {
            RedirectHelper.forwardWithMessage(request, resp, "admin_page", ex.getMessage(), ClassNameGetter.getClassName(ex.getClass().getName()));
        }
    }

    private void updateTable(HttpServletRequest request) throws DBException {
        List<User> users = usersService.findAll();
        request.setAttribute("usersList", users);
    }
}
