package ru.kpfu.itis.gnt.controllers.pages;


import ru.kpfu.itis.gnt.DAO.implementations.UsersRepositoryJDBCTemplateImpl;
import ru.kpfu.itis.gnt.Utils.Encrypter;
import ru.kpfu.itis.gnt.Utils.RedirectHelper;
import ru.kpfu.itis.gnt.constants.ListenerConstants;
import ru.kpfu.itis.gnt.constants.PathsConstants;
import ru.kpfu.itis.gnt.exceptions.DBException;
import ru.kpfu.itis.gnt.services.implementations.UsersService;
import ru.kpfu.itis.gnt.Utils.validators.ClassNameGetter;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/signin")
public class SignInPage extends HttpServlet {

    private UsersService userService;

    @Override
    public void init() throws ServletException {
        userService = new UsersService(
                (UsersRepositoryJDBCTemplateImpl) getServletContext().getAttribute(ListenerConstants.KEY_USER_DAO)
        );
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/signin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            if (email != null && password != null) {
                userService.signIn(req, email, Encrypter.md5Hex(password));
                resp.sendRedirect(getServletContext().getContextPath() + PathsConstants.PROFILE_MAIN_PAGE_PATH);
            }
        } catch (DBException e) {
            RedirectHelper.forwardWithMessage(req, resp, "/main",  e.getMessage(), ClassNameGetter.getClassName(e.getClass().getName()));
        }
        req.setAttribute("errorMessage", "Wrong email or password");
        req.setAttribute("email", req.getParameter("email"));
    }
}
