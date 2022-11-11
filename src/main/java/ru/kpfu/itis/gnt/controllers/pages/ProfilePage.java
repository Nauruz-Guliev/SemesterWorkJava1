package ru.kpfu.itis.gnt.controllers.pages;

import ru.kpfu.itis.gnt.DAO.implementations.UsersRepositoryJDBCTemplateImpl;
import ru.kpfu.itis.gnt.Utils.RedirectHelper;
import ru.kpfu.itis.gnt.constants.FieldsConstants;
import ru.kpfu.itis.gnt.constants.ListenerConstants;
import ru.kpfu.itis.gnt.entities.User;
import ru.kpfu.itis.gnt.exceptions.DBException;
import ru.kpfu.itis.gnt.services.implementations.UsersService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/profile/general")
public class ProfilePage extends HttpServlet {

    private int userId;
    private UsersService usersService;
    private User user;

    private User updatedUser;


    private String firstName;
    private String lastName;
    private String email;
    private String country;
    private String gender;
    private String dateOfBirth;

    @Override
    public void init() {
        usersService = new UsersService(
                (UsersRepositoryJDBCTemplateImpl) getServletContext().getAttribute(ListenerConstants.KEY_USER_DAO)
        );
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            initValues(req, resp);
            req.setAttribute(FieldsConstants.FIELD_USER, user);
            getServletContext().getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);

        } catch (ServletException | DBException | IOException ex) {
            RedirectHelper.forwardWithMessage(req, resp, "profile",  ex.getMessage(), ex.getClass().getName());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        try {
            initAttributes(req);
            updatedUser = new User(
                    firstName,
                    lastName,
                    email,
                    gender,
                    dateOfBirth,
                    country
            );
            updatedUser.setId(userId);
            usersService.updateUser(updatedUser);
            req.setAttribute(FieldsConstants.FIELD_USER, usersService.findUserById(userId));
            RedirectHelper.forwardWithMessage(req, resp, "profile",  "Profile was updated", "Success");
        } catch (DBException | NumberFormatException | IOException | ServletException ex) {
            RedirectHelper.forwardWithMessage(req, resp, "profile",  ex.getMessage(), ex.getClass().getName());
        }
    }

    private void initValues(HttpServletRequest req, HttpServletResponse resp) throws DBException {
        userId = (int) req.getSession().getAttribute(FieldsConstants.USER_ID_ATTRIBUTE);
        user = usersService.findUserById(userId);
    }

    private void initAttributes(HttpServletRequest request) {
        firstName = request.getParameter(FieldsConstants.FIRST_NAME_PARAMETER);
        lastName = request.getParameter(FieldsConstants.LAST_NAME_FIELD_PARAMETER);
        email = request.getParameter(FieldsConstants.EMAIL_FIELD_PARAMETER);
        country = request.getParameter(FieldsConstants.COUNTRY_FIELD_PARAMETER);
        gender = request.getParameter(FieldsConstants.GENDER_FIELD_PARAMETER);
        dateOfBirth = request.getParameter(FieldsConstants.DATE_OF_BIRTH_FIELD_PARAMETER);
    }

}
