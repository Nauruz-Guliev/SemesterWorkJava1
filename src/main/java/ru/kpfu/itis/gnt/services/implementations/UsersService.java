package ru.kpfu.itis.gnt.services.implementations;

import ru.kpfu.itis.gnt.DAO.implementations.UsersRepositoryJDBCTemplateImpl;
import ru.kpfu.itis.gnt.Utils.Encrypter;
import ru.kpfu.itis.gnt.dto.UserSignUp;
import ru.kpfu.itis.gnt.entities.User;
import ru.kpfu.itis.gnt.exceptions.DBException;
import ru.kpfu.itis.gnt.exceptions.RegistrationException;
import ru.kpfu.itis.gnt.validators.RegistrationFieldsValidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

public class UsersService implements ru.kpfu.itis.gnt.services.UsersService {
    private UsersRepositoryJDBCTemplateImpl userDAO;
    private static final String EMAIL = "email";
    private static final String ADMIN = "admin";
    private static final String USER_ID = "USER_ID";

    private List<String> errorList;

    private RegistrationFieldsValidator userValidator;

    public UsersService(UsersRepositoryJDBCTemplateImpl userDAO) {
        this.userDAO = userDAO;
    }

    public Map<String, Object> getAccountInfo(HttpServletRequest req) {
        Map<String, Object> account = new HashMap<>();
        account.put(EMAIL, req.getSession().getAttribute(EMAIL));
        account.put(USER_ID, req.getSession().getAttribute(USER_ID));
        if (isSigned(req)) {
            return account;
        } else if (isAdminSigned(req)) {
            account.put(ADMIN, req.getSession().getAttribute(ADMIN));
            return account;
        }
        return null;
    }

    @Override
    public void updateUser(User user) throws DBException {
        checkUser(user, true, "", "");
        if (!userDAO.updateUser(user)) {
            throw new DBException("Couldn't update user");
        }
    }

    public void removeUser(int userId) throws DBException{
        if (!userDAO.deleteUser(userId)) {
            throw new DBException("Couldn't remove the user!");
        }
    }

    public void updateCountry(String country, int userId) throws DBException {
        if (!userDAO.updateCountry(country, userId))
            throw new DBException("Couldn't update user's country");
    }

    public List<User> findAll() throws DBException {
        return userDAO.findAll().orElseThrow(
                () -> new DBException("Couldn't load all the users")
        );
    }

    @Override
    public boolean signUp(String firstName, String lastName, String email, String password, String passwordConfirm, String gender, String dateOfBirth, String country, String policyAgreement) throws DBException, RegistrationException, IOException {
        User newUser = new User(firstName, lastName, email, Encrypter.md5Hex(password), gender, dateOfBirth, country);
        checkUser(newUser, false, passwordConfirm, policyAgreement);
        if (errorList.isEmpty()) {
            if (!userDAO.saveUser(newUser)) {
                throw new DBException("Couldn't register");
            }

        } else {
            StringBuilder finalErrorMessage = new StringBuilder();
            for (String errorMessage : errorList) {
                finalErrorMessage.append(errorMessage).append("\n");
            }
            throw new RegistrationException(finalErrorMessage.toString());
        }
        return true;
    }

    private void checkUser(User user, Boolean isUpdating, String passwordConfirm, String policyAgreement) {
        if (isUpdating) {
            userValidator = new RegistrationFieldsValidator(
                    user.getFirstName(),
                    user.getLastName(),
                    user.getEmail(),
                    user.getGender(),
                    user.getDateOfBirth(),
                    user.getCountry()
            );
        } else {
            userValidator = new RegistrationFieldsValidator(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), passwordConfirm, user.getGender(), user.getDateOfBirth(), user.getCountry(), policyAgreement);
        }
        errorList = userValidator.getErrorList();
    }

    public boolean isSigned(HttpServletRequest req) {
        return req.getSession().getAttribute(EMAIL) != null;
    }

    public boolean isAdminSigned(HttpServletRequest req) {
        return req.getSession().getAttribute(ADMIN) != null;
    }

    @Override
    public boolean signIn(HttpServletRequest req, String email, String password) throws DBException {
        if (userDAO.findUser(email, password).isPresent()) {
            User user = userDAO.findUser(email, password).get();
            req.getSession().setAttribute(EMAIL, user.getEmail());
            req.getSession().setAttribute(USER_ID, user.getId());
            if (user.getRole().equals(ADMIN)) {
                req.getSession().setAttribute(ADMIN, user.getEmail());
            }
            return true;
        }
        return false;
    }

    @Override
    public void signOut(HttpServletRequest req) {
        req.getSession().removeAttribute(EMAIL);
        req.getSession().removeAttribute(USER_ID);
        if (req.getSession().getAttribute(ADMIN) != null) req.getSession().removeAttribute(ADMIN);
    }

    public User findUserById(int userId) throws DBException {
        return userDAO.findById(userId).orElseThrow(
                () -> new DBException("User was not found")
        );
    }

}
