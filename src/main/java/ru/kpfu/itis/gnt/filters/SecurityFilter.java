package ru.kpfu.itis.gnt.filters;


import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.kpfu.itis.gnt.DAO.UsersRepositoryJDBCTemplateImpl;
import ru.kpfu.itis.gnt.exceptions.DBException;
import ru.kpfu.itis.gnt.services.SecurityService;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebFilter("/*")
public class SecurityFilter extends HttpFilter {
    protected final String[] protectedPaths = {"/profile"};

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
            UsersRepositoryJDBCTemplateImpl usersRepositoryJDBCTemplate = (UsersRepositoryJDBCTemplateImpl) getServletContext().getAttribute("USERDAO");
            SecurityService securityService = new SecurityService(usersRepositoryJDBCTemplate);

            boolean isUserSigned = securityService.isSigned(req);
            if (prot && !isUserSigned) {
                res.sendRedirect(req.getContextPath() + "/main");
            }  else {
                if (isUserSigned) {
                    req.setAttribute(USER, securityService.getAccountInfo(req));
                }
                chain.doFilter(req, res);
            }
        } catch (DBException e) {
            throw new RuntimeException(e);
        }
    }
}
