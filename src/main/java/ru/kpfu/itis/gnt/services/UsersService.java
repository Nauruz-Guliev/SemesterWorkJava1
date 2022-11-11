package ru.kpfu.itis.gnt.services;

import ru.kpfu.itis.gnt.entities.User;
import ru.kpfu.itis.gnt.exceptions.DBException;
import ru.kpfu.itis.gnt.exceptions.RegistrationException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface UsersService {
    void signIn(HttpServletRequest req, String email,String password) throws DBException;
    void signOut(HttpServletRequest req);
    boolean signUp(String firstName, String lastName, String email, String password, String passwordConfirm, String gender, String dateOfBirth, String country, String policyAgreement) throws DBException, RegistrationException, IOException;
    void updateUser(User user) throws DBException, IOException;
}
