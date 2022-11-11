package ru.kpfu.itis.gnt.controllers.servlets;

import ru.kpfu.itis.gnt.DAO.implementations.UsersRepositoryJDBCTemplateImpl;
import ru.kpfu.itis.gnt.Utils.RedirectHelper;
import ru.kpfu.itis.gnt.constants.ListenerConstants;
import ru.kpfu.itis.gnt.services.implementations.UsersService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/signout")
public class SignOutServlet extends HttpServlet {

    private UsersService usersService;
    @Override
    public void init() {
        usersService = new UsersService(
                (UsersRepositoryJDBCTemplateImpl)  getServletContext().getAttribute(ListenerConstants.KEY_USER_DAO)
        );
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            usersService.signOut(req);
            resp.sendRedirect(getServletContext().getContextPath() + "/");
        } catch (IOException e) {
            RedirectHelper.forwardWithMessage(req, resp, "main",  e.getMessage(), e.getClass().getName());
        }
    }

}
