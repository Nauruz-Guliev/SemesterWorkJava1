package ru.kpfu.itis.gnt.filters;

import ru.kpfu.itis.gnt.DAO.implementations.UsersRepositoryJDBCTemplateImpl;
import ru.kpfu.itis.gnt.constants.ListenerConstants;
import ru.kpfu.itis.gnt.services.implementations.UsersService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/admin")
public class AdminPageFilter extends HttpFilter {

    private boolean isAdminPage;

    private UsersService usersService;
    @Override
    public void init() throws ServletException {
        usersService = new UsersService(
                (UsersRepositoryJDBCTemplateImpl) getServletContext().getAttribute(ListenerConstants.KEY_USER_DAO)
        );
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) {
        if (req.getRequestURI().substring(req.getContextPath().length()).equals("/admin")) {
            isAdminPage = true;
        }
        try {
            boolean isAdminSigned = usersService.isAdminSigned(req);
            if (isAdminPage && !isAdminSigned) {
                res.sendRedirect(req.getContextPath() + "/main");
            } else {
                if (isAdminSigned) {
                    req.setAttribute("admin", usersService.getAccountInfo(req));
                }
                chain.doFilter(req, res);
            }
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
