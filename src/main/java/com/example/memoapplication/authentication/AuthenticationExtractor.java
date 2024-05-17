package com.example.memoapplication.authentication;

import com.example.memoapplication.errorcode.ErrorCode;
import com.example.memoapplication.exception.NotFoundException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class AuthenticationExtractor {
    private static final String TOKEN_COOKIE_NAME = "AccessToken";

    //JWT Token 추출
    public static String extract(final HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (TOKEN_COOKIE_NAME.equals(cookie.getName())) {
                    //"Bearer " 를 제외한 뒷 부분 jwt token 값만 추출
                    return cookie.getValue().replace("=", " ").substring(7);
                }
            }
        }
        throw new NotFoundException(ErrorCode.TOKEN_NOT_FOUND);
    }
}
