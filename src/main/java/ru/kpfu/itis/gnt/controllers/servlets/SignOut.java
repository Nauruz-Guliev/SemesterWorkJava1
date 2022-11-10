package ru.kpfu.itis.gnt.controllers.servlets;

import ru.kpfu.itis.gnt.DAO.implementations.UsersRepositoryJDBCTemplateImpl;
import ru.kpfu.itis.gnt.constants.ListenerConstants;
import ru.kpfu.itis.gnt.services.implementations.UsersService;

import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/signout")
public class SignOut extends HttpServlet {

    private UsersService usersService;
    @Override
    public void init() {
        usersService = new UsersService(
                (UsersRepositoryJDBCTemplateImpl)  getServletContext().getAttribute(ListenerConstants.KEY_USER_DAO)
        );
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            usersService.signOut(req);
            resp.sendRedirect(getServletContext().getContextPath() + "/");
        } catch (IOException e) {
            throw new RuntimeException("");
        }
    }

}
