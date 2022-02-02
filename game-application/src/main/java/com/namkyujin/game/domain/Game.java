package com.namkyujin.game.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.time.LocalDateTime;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Game {

    @Getter
    private final GameId id;

    @Getter
    private final GameRoomId roomId;

    @Getter
    private final GameToken token;

    @Getter
    private final AwardAmount amount;

    @Getter
    private final Integer totalWinners;

    @Getter
    private final GameRoomUserId createdBy;

    @Getter
    private final LocalDateTime createdAt;

    public static Game withoutId(GameRoomId roomId, GameToken token, AwardAmount amount, Integer totalWinners, GameRoomUserId createdBy) {
        return withId(null, roomId, token, amount, totalWinners, createdBy, null);
    }

    public static Game withId(GameId id, GameRoomId roomId, GameToken token, AwardAmount amount, Integer totalWinners, GameRoomUserId createdBy, LocalDateTime createdAt) {
        return new Game(id, roomId, token, amount, totalWinners, createdBy, createdAt);
    }

    public boolean isMine(GameRoomUserId userId) {
        return createdBy.equals(userId);
    }


    @Value
    public static class GameId {
        private Long value;
    }
}
