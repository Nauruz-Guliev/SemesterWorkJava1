package ru.kpfu.itis.gnt.Utils;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RedirectHelper {
    public static void forwardWithMessage(HttpServletRequest request, HttpServletResponse response, String path, String message, String titleName) throws IOException, ServletException {
        request.setAttribute("popupMessageBody", message);
        request.setAttribute("popupMessageTitle", titleName);
        request.getRequestDispatcher("/WEB-INF/views/" + path + ".jsp").forward(request, response);
    }
}
