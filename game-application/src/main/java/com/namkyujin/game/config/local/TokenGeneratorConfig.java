package com.namkyujin.game.config.local;

import com.namkyujin.game.core.domain.token.TokenRepository;
import com.namkyujin.game.token.application.TokenInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"local"})
@Configuration
public class TokenGeneratorConfig {
    @Bean
    public TokenInitializer tokenInitializer(@Autowired TokenRepository tokenRepository) {
        return new TokenInitializer(tokenRepository);
    }
}
