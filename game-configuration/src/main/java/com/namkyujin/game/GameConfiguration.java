package com.namkyujin.game;

import com.namkyujin.game.application.service.TokenInitializer;
import com.namkyujin.game.domain.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class GameConfiguration {

    @Profile({"local"})
    @Bean
    public TokenInitializer tokenInitializer(@Autowired TokenRepository tokenRepository) {
        return new TokenInitializer(tokenRepository);
    }
}
