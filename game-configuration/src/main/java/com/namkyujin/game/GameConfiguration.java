package com.namkyujin.game;

import com.namkyujin.game.application.port.out.CreateTokenPort;
import com.namkyujin.game.domain.GameAwardRule;
import com.namkyujin.game.domain.GameAwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
public class GameConfiguration {

    /**
     * 로컬에서 개발 편의를 위해 임의로 토큰을 생성함
     */
    @Profile({"local"})
    @Bean
    public TokenInitializer tokenInitializer(@Autowired CreateTokenPort createTokenPort) {
        return new TokenInitializer(createTokenPort);
    }

    @Bean
    public GameAwardService gameAwardService() {
        return new GameAwardService(gameAwardRule());
    }

    private GameAwardRule gameAwardRule() {
        return new GameAwardRule.Composite(
                Arrays.asList(
                        new GameAwardRule.AllInOne(),
                        new GameAwardRule.Balanced()
                )
        );
    }
}
