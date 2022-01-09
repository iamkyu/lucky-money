package com.namkyujin.lucky.game.model.api;

import lombok.*;

import javax.validation.constraints.Min;

@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CreateGameRequest {

    @Min(value = 1, message = "뿌릴 금액은 최소 {value}원 이상 입니다.")
    private int amount;

    @Min(value = 1, message = "당첨자는 최소 {value}명 이상입니다.")
    private int totalWinners;

    public CreateGameRequest(int amount, int totalWinners) {
        this.amount = amount;
        this.totalWinners = totalWinners;
    }

    public boolean winnersGreaterThenAmount() {
        return totalWinners > amount;
    }
}
