package ru.kpfu.itis.gnt.controllers.pages;


import ru.kpfu.itis.gnt.DAO.implementations.UsersRepositoryJDBCTemplateImpl;
import ru.kpfu.itis.gnt.Utils.Encrypter;
import ru.kpfu.itis.gnt.constants.CookieConstants;
import ru.kpfu.itis.gnt.constants.FieldsConstants;
import ru.kpfu.itis.gnt.constants.ListenerConstants;
import ru.kpfu.itis.gnt.entities.User;
import ru.kpfu.itis.gnt.exceptions.DBException;
import ru.kpfu.itis.gnt.services.implementations.UsersAuthenticationService;
import ru.kpfu.itis.gnt.services.implementations.UsersServiceImpl;
import ru.kpfu.itis.gnt.validators.RegistrationFieldsValidator;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;

@WebServlet("/register")
public class Registration extends HttpServlet {

    private String simpleDateFormat = "yyyy-MM-dd HH:mm:ss.SSSS";
    private RegistrationFieldsValidator validator;
    private ArrayList<String> errorList;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String passwordConfirm;
    private String country;
    private String gender;
    private String dateOfBirth;
    private String policeAgreement;
    private UsersAuthenticationService usersService;

    @Override
    public void init() {
        usersService = new UsersAuthenticationService(
                (UsersRepositoryJDBCTemplateImpl) getServletContext().getAttribute(ListenerConstants.KEY_USER_DAO)
        );
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            initValues(request);
            validator = new RegistrationFieldsValidator(firstName, lastName, email, password, passwordConfirm, gender, dateOfBirth, country, policeAgreement);
            errorList = validator.getErrorList();
            request.setAttribute(FieldsConstants.ERROR_LIST, errorList);
            if (errorList.isEmpty()) {
                User newUser = new User(firstName, lastName, email, Encrypter.md5Hex(password), gender, dateOfBirth, country);
                usersService.saveUser(newUser);
                request.getRequestDispatcher("/WEB-INF/views/signin.jsp").forward(request, response);
            }
            if (!errorList.isEmpty()) {
                request.setAttribute(FieldsConstants.ERROR_LIST, errorList);
            }
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
        } catch (DBException ex) {
            response.addCookie(
                    new Cookie(
                            CookieConstants.ERROR_MESSAGE,
                            ex.getMessage()
                    )
            );
        }
    }


    private void initValues(HttpServletRequest request) throws IOException {
        firstName = request.getParameter(FieldsConstants.FIRST_NAME_PARAMETER);
        lastName = request.getParameter(FieldsConstants.LAST_NAME_FIELD_PARAMETER);
        email = request.getParameter(FieldsConstants.EMAIL_FIELD_PARAMETER);
        password = request.getParameter(FieldsConstants.PASSWORD_FIELD_PARAMETER);
        passwordConfirm = request.getParameter(FieldsConstants.PASSWORD_CONFIRM_FIELD_PARAMETER);
        country = request.getParameter(FieldsConstants.COUNTRY_FIELD_PARAMETER);
        gender = request.getParameter(FieldsConstants.GENDER_FIELD_PARAMETER);
        dateOfBirth = request.getParameter(FieldsConstants.DATE_OF_BIRTH_FIELD_PARAMETER);
        policeAgreement = request.getParameter(FieldsConstants.POLICY_AGREEMENT_FIELD_PARAMETER);
        validator = new RegistrationFieldsValidator(firstName, lastName, email, Encrypter.md5Hex(password), Encrypter.md5Hex(passwordConfirm), gender, dateOfBirth, country, policeAgreement);
        errorList = validator.getErrorList();
    }
}
