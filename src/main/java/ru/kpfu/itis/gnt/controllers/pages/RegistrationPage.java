package ru.kpfu.itis.gnt.controllers.pages;


import ru.kpfu.itis.gnt.DAO.implementations.UsersRepositoryJDBCTemplateImpl;
import ru.kpfu.itis.gnt.Utils.RedirectHelper;
import ru.kpfu.itis.gnt.constants.FieldsConstants;
import ru.kpfu.itis.gnt.constants.ListenerConstants;
import ru.kpfu.itis.gnt.exceptions.DBException;
import ru.kpfu.itis.gnt.exceptions.RegistrationException;
import ru.kpfu.itis.gnt.services.implementations.UsersService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegistrationPage extends HttpServlet {

    private String simpleDateFormat = "yyyy-MM-dd HH:mm:ss.SSSS";

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String passwordConfirm;
    private String country;
    private String gender;
    private String dateOfBirth;
    private String policeAgreement;
    private UsersService usersService;

    @Override
    public void init() {
        usersService = new UsersService(
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
            if(usersService.signUp(firstName, lastName, email, password, passwordConfirm, gender, dateOfBirth, country, policeAgreement)){

                RedirectHelper.forwardWithMessage(request, response, "signin",  "Registered successfully", "Success");

            } else {
                RedirectHelper.forwardWithMessage(request, response, "signin",  "Couldn't register", "Error");
            }
        } catch (DBException | RegistrationException ex) {
            RedirectHelper.forwardWithMessage(request, response, "signin",  ex.getMessage(), ex.getClass().getName());

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
    }
}
