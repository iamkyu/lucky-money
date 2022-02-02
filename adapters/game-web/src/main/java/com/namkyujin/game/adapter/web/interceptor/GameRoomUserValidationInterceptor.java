package com.namkyujin.game.adapter.web.interceptor;

import com.namkyujin.game.application.service.exception.UnauthorizedGameRoomUserException;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GameRoomUserValidationInterceptor implements HandlerInterceptor {
    private static final String ROOM_ID_HEADER = "X-ROOM-ID";
    private static final String USER_ID_HEADER = "X-USER-ID";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String roomId = request.getHeader(ROOM_ID_HEADER);
        String userId = request.getHeader(USER_ID_HEADER);

        if (StringUtils.isEmpty(roomId) && StringUtils.isEmpty(userId)) {
            throw new UnauthorizedGameRoomUserException();
        }

        return true;
    }
}
