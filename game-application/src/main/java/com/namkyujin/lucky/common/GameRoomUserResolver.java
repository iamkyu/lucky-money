package com.namkyujin.lucky.common;

import com.namkyujin.lucky.game.model.GameRoomUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
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

        String roomId = webRequest.getHeader(ROOM_ID_HEADER);
        String userId = webRequest.getHeader(USER_ID_HEADER);

        if (StringUtils.isEmpty(roomId) && StringUtils.isEmpty(userId)) {
            log.error("Invalid room user request. roomId={}, userId={}", roomId, userId);
            return GameRoomUser.anonymous();
        }

        GameRoomUser.GameRoomUserBuilder gameRoomUserBuilder = GameRoomUser.builder().roomId(roomId);
        if (!StringUtils.isEmpty(userId)) {
            try {
                gameRoomUserBuilder.userId(Long.parseLong(userId));
            } catch (NumberFormatException e) {
                log.error("Invalid room user request. userId={}", userId);
            }
        }

        return gameRoomUserBuilder.build();
    }
}
