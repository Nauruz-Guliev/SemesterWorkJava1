package ru.kpfu.itis.gnt.Utils;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RedirectHelper {
    public static void redirect(HttpServletRequest request,  HttpServletResponse response, String path, String message) throws IOException {
        response.sendRedirect(request.getContextPath() + path + "?message="+ message);
    }
    public static void showExistingPopupMessage(HttpServletResponse response, HttpServletRequest request){
        String message = request.getParameter("message");
        if (message != null) {
            CookieMessageAdder.addMessage(response, "Message", message);
        }
    }
}
