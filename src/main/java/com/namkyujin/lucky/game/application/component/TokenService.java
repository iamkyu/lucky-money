package com.namkyujin.lucky.game.application.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class TokenService {

    String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "0123456789"
            + "abcdefghijklmnopqrstuvxyz";

    // 비교시 아스키코드로 비교해야 함.

    public String generate(String roomId) {
        // TODO
        int i = ThreadLocalRandom.current().nextInt(1000);
        return String.valueOf(i);
//        return "abc";
    }
}
