package com.namkyujin.game.application.port.in;

import com.namkyujin.game.domain.AwardAmount;
import com.namkyujin.game.domain.GameRoomUser;
import com.namkyujin.game.domain.GameToken;
import lombok.Getter;

public interface JoinGameUseCase {

    WinningPrizePresentation join(GameRoomUser gameRoomUser, GameToken token);

    @Getter
    class WinningPrizePresentation {
        private final Long prizeAmount;

        public WinningPrizePresentation(AwardAmount amount) {
            this.prizeAmount = amount.getValue();
        }
    }
}
