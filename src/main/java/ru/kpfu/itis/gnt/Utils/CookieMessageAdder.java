package ru.kpfu.itis.gnt.Utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieMessageAdder {
    public static void addMessage(HttpServletResponse response, String key, String value) {
        response.addCookie(
                new Cookie(
                        key,
                        value.replace(" ", "-")
                )
        );
    }
}
