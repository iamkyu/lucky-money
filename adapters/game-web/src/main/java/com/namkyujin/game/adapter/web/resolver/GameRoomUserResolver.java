package com.namkyujin.game.adapter.web.resolver;

import com.namkyujin.game.domain.GameRoomUser;
import com.namkyujin.game.domain.GameRoomId;
import com.namkyujin.game.domain.GameRoomUserId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
public class GameRoomUserResolver implements HandlerMethodArgumentResolver {
    private static final String ROOM_ID_HEADER = "X-ROOM-ID";
    private static final String USER_ID_HEADER = "X-USER-ID";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return GameRoomUser.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        GameRoomId gameRoomId = new GameRoomId(webRequest.getHeader(ROOM_ID_HEADER));
        String userIdInHeader = webRequest.getHeader(USER_ID_HEADER);
        GameRoomUserId gameRoomUserId = null;
        try {
            gameRoomUserId = new GameRoomUserId(Long.parseLong(userIdInHeader));
        } catch (NumberFormatException e) {
            log.error("Invalid room user request. userId={}", userIdInHeader);
        }

        return new GameRoomUser(gameRoomId, gameRoomUserId);
    }
}
