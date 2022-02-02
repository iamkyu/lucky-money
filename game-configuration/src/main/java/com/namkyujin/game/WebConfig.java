package com.namkyujin.game;

import com.namkyujin.game.adapter.web.resolver.GameRoomUserResolver;
import com.namkyujin.game.adapter.web.interceptor.GameRoomUserValidationInterceptor;
import com.namkyujin.game.adapter.web.resolver.GameTokenResolver;
import com.namkyujin.game.adapter.web.interceptor.TokenValidationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new GameRoomUserResolver());
        resolvers.add(new GameTokenResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenValidationInterceptor())
                .addPathPatterns("/v1/lucky");
        registry.addInterceptor(new GameRoomUserValidationInterceptor())
                .addPathPatterns("/v1/lucky");
    }
}
