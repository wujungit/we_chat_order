package com.webank.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Cookie工具类
 */
public class CookieUtil {
    /**
     * 设置Cookie
     *
     * @param response
     * @param name
     * @param value
     * @param maxAge
     */
    public static void set(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 获取Cookie
     *
     * @param request
     * @param name
     * @return
     */
    public static Cookie get(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        // 将Cookie数组转Map格式
        Map<String, Cookie> map = new HashMap<>();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                map.put(cookie.getName(), cookie);
            }
        }
        return map.getOrDefault(name, null);
    }
}
