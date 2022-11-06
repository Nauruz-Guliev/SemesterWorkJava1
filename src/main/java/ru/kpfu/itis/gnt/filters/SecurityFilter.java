package ru.kpfu.itis.gnt.filters;


import ru.kpfu.itis.gnt.exceptions.DBException;
import ru.kpfu.itis.gnt.services.UsersAuthenticationService;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebFilter("/*")
public class SecurityFilter extends HttpFilter {
    protected final String[] protectedPaths = {"/profile", "/comment"};

    private final static String USER = "user";

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        boolean prot = false;

        for (String path : protectedPaths) {
            if (path.equals(req.getRequestURI().substring(req.getContextPath().length()))) {
                prot = true;
                break;
            }
        }

        try {
            UsersAuthenticationService usersAuthenticationService = new UsersAuthenticationService(getServletContext());

            boolean isUserSigned = usersAuthenticationService.isSigned(req);
            if (prot && !isUserSigned) {
                res.sendRedirect(req.getContextPath() + "/main");
            }  else {
                if (isUserSigned) {
                    req.setAttribute(USER, usersAuthenticationService.getAccountInfo(req));
                }
                chain.doFilter(req, res);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
