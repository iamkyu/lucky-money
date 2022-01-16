package com.namkyujin.game.game.model.api;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class WiningPrizeRequest {
    @NotEmpty(message = "토큰은 필수입니다.")
    private String token;
}
