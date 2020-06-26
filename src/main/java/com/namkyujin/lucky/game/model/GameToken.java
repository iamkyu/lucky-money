package com.namkyujin.lucky.game.model;

import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor(staticName = "of")
public class GameToken {
    private final String token;

    public String get() {
        return token;
    }

    public boolean invalid() {
        return StringUtils.isEmpty(token);
    }
}
