package com.namkyujin.game.adapter.web.interceptor;

import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenValidationInterceptor implements HandlerInterceptor {
    private static final String TOKEN_HEADER = "X-GAME-TOKEN";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (HttpMethod.POST.name().equals(request.getMethod())) {
            return true;
        }

        String token = request.getHeader(TOKEN_HEADER);

        if (StringUtils.isEmpty(token)) {
            throw new IllegalArgumentException("게임 정보가 올바르지 않습니다.");
        }

        return true;
    }
}
