package com.namkyujin.game.application.port.in;

import com.namkyujin.game.domain.AwardAmount;
import com.namkyujin.game.domain.GameRoomUser;
import com.namkyujin.game.domain.GameRoomUserId;
import com.namkyujin.game.domain.GameToken;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public interface GetGameStatusQuery {

    GameStatusPresentation getStatus(GameRoomUser gameRoomUser, GameToken token);

    @Getter
    class GameStatusPresentation {
        private final LocalDateTime gameCreatedAt;
        private final Long amount;
        private final Long completeAmount;
        private List<Winner> winners;

        public GameStatusPresentation(LocalDateTime gameCreatedAt, AwardAmount amount, AwardAmount completeAmount, List<Winner> winners) {
            this.gameCreatedAt = gameCreatedAt;
            this.amount = amount.getValue();
            this.completeAmount = completeAmount.getValue();
            this.winners = winners;
        }

        @Getter
        public static class Winner {
            private final Long amount;
            private final Long userId;

            public Winner(AwardAmount amount, GameRoomUserId userId) {
                this.amount = amount.getValue();
                this.userId = userId.getValue();
            }
        }
    }
}
