package ru.kpfu.itis.gnt.filters;


import ru.kpfu.itis.gnt.DAO.implementations.UsersRepositoryJDBCTemplateImpl;
import ru.kpfu.itis.gnt.constants.ListenerConstants;
import ru.kpfu.itis.gnt.services.UsersService;
import ru.kpfu.itis.gnt.services.implementations.UsersAuthenticationService;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebFilter("/*")
public class SecurityFilter extends HttpFilter {
    protected final String[] protectedPaths = {"/profile/general", "/comment", "profile/security", "profile/update"};

    private final static String USER = "user";

    private  UsersAuthenticationService userService;

    @Override
    public void init() {
        userService = new UsersAuthenticationService(
                (UsersRepositoryJDBCTemplateImpl) getServletContext().getAttribute(ListenerConstants.KEY_USER_DAO)
        );
    }

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

            boolean isUserSigned = userService.isSigned(req);
            if (prot && !isUserSigned) {
                res.sendRedirect(req.getContextPath() + "/main");
            }  else {
                if (isUserSigned) {
                    req.setAttribute(USER, userService.getAccountInfo(req));
                }
                chain.doFilter(req, res);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
