package ru.kpfu.itis.gnt.controllers.pages;

import ru.kpfu.itis.gnt.DAO.implementations.UsersRepositoryJDBCTemplateImpl;
import ru.kpfu.itis.gnt.constants.FieldsConstants;
import ru.kpfu.itis.gnt.constants.ListenerConstants;
import ru.kpfu.itis.gnt.services.implementations.UsersAuthenticationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/profile/security")
public class UpdateUserSecurityServlet extends HttpServlet {

    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;
    private UsersAuthenticationService userService;

    @Override
    public void init() throws ServletException {
        userService = new UsersAuthenticationService(
                (UsersRepositoryJDBCTemplateImpl) getServletContext().getAttribute(ListenerConstants.KEY_USER_DAO)
        );
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/profile_security_page.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        initFields(req);

        getServletContext().getRequestDispatcher("/WEB-INF/views/profile_security_page.jsp").forward(req, resp);
    }

    private void initFields(HttpServletRequest req) {
        newPassword = req.getParameter(FieldsConstants.PASSWORD_FIELD_PARAMETER);
        confirmNewPassword = req.getParameter(FieldsConstants.PASSWORD_CONFIRM_FIELD_PARAMETER);
        oldPassword = req.getParameter(FieldsConstants.PASSWORD_OLD_PARAMETER);
    }
}
