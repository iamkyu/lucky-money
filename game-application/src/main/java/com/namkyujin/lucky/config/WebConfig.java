package com.namkyujin.lucky.config;

import com.namkyujin.lucky.common.GameRoomUserResolver;
import com.namkyujin.lucky.common.GameTokenResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new GameRoomUserResolver());
        resolvers.add(new GameTokenResolver());
    }
}
