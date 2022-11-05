package ru.kpfu.itis.gnt.controllers;


import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.kpfu.itis.gnt.DAO.UsersRepositoryJDBCTemplateImpl;
import ru.kpfu.itis.gnt.Utils.Encrypter;
import ru.kpfu.itis.gnt.entities.User;
import ru.kpfu.itis.gnt.validators.RegistrationFieldsValidator;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        initValues(request);
        validator = new RegistrationFieldsValidator(firstName, lastName, email, password, passwordConfirm, gender, dateOfBirth, country, policeAgreement);
        errorList = validator.getErrorList();
        request.setAttribute("errorList", errorList);

        if (errorList.isEmpty()) {
            User newUser = new User(firstName, lastName, email, Encrypter.md5Hex(password), gender, dateOfBirth, country);
            if (!saveRegisteredAccount(newUser)) {
                errorList.add("Couldn't register because such a user already exists or because of internal database issues.");
            } else {
                request.getRequestDispatcher("/WEB-INF/views/signin.jsp").forward(request, response);
            }
        }
        if (!errorList.isEmpty()) {
            request.setAttribute("errorList", errorList);
        }
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    private boolean saveRegisteredAccount(User user) throws RuntimeException {
        try  {
            UsersRepositoryJDBCTemplateImpl userDAO = (UsersRepositoryJDBCTemplateImpl) getServletContext().getAttribute("USERDAO");
            return userDAO.saveUser(user);
        } catch (Exception ex) {
            throw new RuntimeException("User could not be saved due to " + ex.getMessage());
        }
    }


    private void initValues(HttpServletRequest request) throws IOException {
        firstName = request.getParameter("firstName");
        lastName = request.getParameter("lastName");
        email = request.getParameter("email");
        password = request.getParameter("password");
        passwordConfirm = request.getParameter("password-confirm");
        country = request.getParameter("country");
        gender = request.getParameter("gender");
        dateOfBirth = request.getParameter("date-of-birth");
        policeAgreement = request.getParameter("policy-agreement");
        validator = new RegistrationFieldsValidator(firstName, lastName, email, Encrypter.md5Hex(password), Encrypter.md5Hex(passwordConfirm), gender, dateOfBirth, country, policeAgreement);
        errorList = validator.getErrorList();
    }
}
