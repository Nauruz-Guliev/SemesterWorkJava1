package ru.kpfu.itis.gnt.services.implementations;

import ru.kpfu.itis.gnt.DAO.implementations.UsersRepositoryJDBCTemplateImpl;
import ru.kpfu.itis.gnt.entities.User;
import ru.kpfu.itis.gnt.exceptions.DBException;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

public class UsersAuthenticationService {
    private UsersRepositoryJDBCTemplateImpl userDAO;
    private static final String EMAIL = "email";
    private static final String ADMIN = "admin";
    private static final String USER_ID = "USER_ID";

    public UsersAuthenticationService(ServletContext context) {
        this.userDAO = (UsersRepositoryJDBCTemplateImpl) context.getAttribute("USERDAO");
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

    public boolean isSigned(HttpServletRequest req) {
        return req.getSession().getAttribute(EMAIL) != null;
    }

    public boolean isAdminSigned(HttpServletRequest req) {
        return req.getSession().getAttribute(ADMIN) != null;
    }

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
