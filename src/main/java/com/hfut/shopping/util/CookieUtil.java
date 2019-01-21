package com.hfut.shopping.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

	public static void set(HttpServletResponse resp,String name,String value,int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAge);
		resp.addCookie(cookie);
	}
}
