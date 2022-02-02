package com.namkyujin.game.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
public class GameWinner {
    @Getter
    private final WinnerId id;

    @Getter
    private final Game.GameId gameId;

    @Getter
    private final GameRoomUserId gameRoomUserId;

    @Getter
    private final AwardAmount amount;

    @Value
    public static class WinnerId {
        private Long value;
    }
}
