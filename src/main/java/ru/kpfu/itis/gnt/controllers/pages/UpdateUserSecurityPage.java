package ru.kpfu.itis.gnt.controllers.pages;

import ru.kpfu.itis.gnt.DAO.implementations.UsersRepositoryJDBCTemplateImpl;
import ru.kpfu.itis.gnt.Utils.RedirectHelper;
import ru.kpfu.itis.gnt.constants.FieldsConstants;
import ru.kpfu.itis.gnt.constants.ListenerConstants;
import ru.kpfu.itis.gnt.exceptions.AuthenticationException;
import ru.kpfu.itis.gnt.exceptions.DBException;
import ru.kpfu.itis.gnt.exceptions.EmptyResultDbException;
import ru.kpfu.itis.gnt.exceptions.ValidationException;
import ru.kpfu.itis.gnt.services.implementations.UsersService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebServlet("/profile/security")
public class UpdateUserSecurityPage extends HttpServlet {

    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;
    private int userId;
    private String email;
    private UsersService userService;

    @Override
    public void init() throws ServletException {
        userService = new UsersService(
                (UsersRepositoryJDBCTemplateImpl) getServletContext().getAttribute(ListenerConstants.KEY_USER_DAO)
        );

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            userId = (int) req.getSession().getAttribute(FieldsConstants.USER_ID_ATTRIBUTE);
            email = (String) req.getSession().getAttribute(FieldsConstants.EMAIL_FIELD_PARAMETER);
            if (Objects.equals(req.getParameter("confirmed"), "true")) {
                System.out.println(userId);
                userService.removeUser(userId);
                userService.signOut(req);
                System.out.println("ASDASDASDASD");
                resp.sendRedirect(req.getContextPath() + "/main");
            } else {
                System.out.println("ASDASDASD");
                getServletContext().getRequestDispatcher("/WEB-INF/views/profile_security_page.jsp").forward(req, resp);
            }
        } catch (DBException ex) {
            System.out.println(ex.getMessage());
            RedirectHelper.forwardWithMessage(req, resp, "/profile/security", ex.getMessage(), ex.getClass().getName());
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        initFields(req);
        System.out.println(newPassword + " " + confirmNewPassword + " " + email + " " +oldPassword);
        try {
            userService.updateUserSecurityInformation(
                    userId,
                    email,
                    newPassword,
                    confirmNewPassword,
                    oldPassword
            );
        } catch (EmptyResultDbException | AuthenticationException | ValidationException e) {
            throw new RuntimeException(e);
        }
        getServletContext().getRequestDispatcher("/WEB-INF/views/profile_security_page.jsp").forward(req, resp);
    }

    private void initFields(HttpServletRequest req) {
        newPassword = req.getParameter(FieldsConstants.PASSWORD_FIELD_PARAMETER);
        confirmNewPassword = req.getParameter(FieldsConstants.PASSWORD_CONFIRM_FIELD_PARAMETER);
        oldPassword = req.getParameter(FieldsConstants.PASSWORD_OLD_PARAMETER);
    }
}
