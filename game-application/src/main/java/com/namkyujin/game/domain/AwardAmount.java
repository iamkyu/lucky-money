package com.namkyujin.game.domain;

import lombok.Value;

@Value
public class AwardAmount {
    private final Long value;

    public AwardAmount add(AwardAmount amount) {
        return new AwardAmount(value + amount.getValue());
    }
}
