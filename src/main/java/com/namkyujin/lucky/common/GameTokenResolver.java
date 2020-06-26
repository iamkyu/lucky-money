package com.namkyujin.lucky.common;

import com.namkyujin.lucky.game.model.GameToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
public class GameTokenResolver implements HandlerMethodArgumentResolver {
    private static final String TOKEN_HEADER = "X-GAME-TOKEN";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return GameToken.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String tokenId = webRequest.getHeader(TOKEN_HEADER);
        return GameToken.of(tokenId);
    }
}
