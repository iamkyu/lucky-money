package com.namkyujin.game.domain;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Getter
@RequiredArgsConstructor
public class GameAward {

    private final GameAwardId id;

    private final Long sequence;

    private final Game.GameId gameId;

    private final AwardAmount amount;

    private final Status status;


    public static GameAward withoutId(Long sequence, Game.GameId gameId, AwardAmount amount) {
        return withId(null, sequence, gameId, amount, Status.NO_WINNER);
    }

    public static GameAward withId(GameAwardId id, Long sequence, Game.GameId gameId, AwardAmount amount, Status status) {
        return new GameAward(id, sequence, gameId, amount, status);
    }

    public GameAward win() {
        return new GameAward(this.id, this.sequence, this.gameId, this.amount, Status.WIN);
    }


    public enum Status {
        NO_WINNER("당첨자 없음"),
        WIN("당첨자 있음");

        private String description;

        Status(String description) {
            this.description = description;
        }


    }

    @Value
    public static class GameAwardId {
        private Long value;
    }
}
