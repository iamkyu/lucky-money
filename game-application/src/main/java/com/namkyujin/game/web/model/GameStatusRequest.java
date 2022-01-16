package com.namkyujin.game.web.model;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class GameStatusRequest {
    @NotEmpty(message = "토큰은 필수입니다.")
    private String token;
}
