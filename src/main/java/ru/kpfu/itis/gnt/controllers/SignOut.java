package ru.kpfu.itis.gnt.controllers;

import ru.kpfu.itis.gnt.exceptions.DBException;
import ru.kpfu.itis.gnt.services.UsersAuthenticationService;

import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/signout")
public class SignOut extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            UsersAuthenticationService usersAuthenticationService = new UsersAuthenticationService(getServletContext());
            usersAuthenticationService.signOut(req);
            resp.sendRedirect(getServletContext().getContextPath() + "/");
        } catch (IOException e) {
            throw new RuntimeException("");
        }
    }

}
