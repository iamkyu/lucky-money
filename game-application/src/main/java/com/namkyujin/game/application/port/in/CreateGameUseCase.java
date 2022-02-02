package com.namkyujin.game.application.port.in;

import com.namkyujin.common.SelfValidating;
import com.namkyujin.game.domain.GameToken;
import com.namkyujin.game.domain.GameRoomUser;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Value;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public interface CreateGameUseCase {

    CreatedGamePresentation createGame(GameRoomUser gameUser, CreateGameCommand command);

    @Value
    @EqualsAndHashCode(callSuper = false)
    class CreateGameCommand extends SelfValidating<CreateGameCommand> {

        @NotNull
        @Min(value = 1, message = "뿌릴 금액은 최소 {value}원 이상 입니다.")
        private final Long amount;

        @NotNull
        @Min(value = 1, message = "당첨자는 최소 {value}명 이상입니다.")
        private final Integer totalWinners;

        public CreateGameCommand(Long amount, Integer totalWinners) {
            this.amount = amount;
            this.totalWinners = totalWinners;
            this.validateSelf();
        }
    }

    @Getter
    class CreatedGamePresentation {
        private final String token;

        public CreatedGamePresentation(GameToken gameToken) {
            this.token = gameToken.getValue();
        }
    }
}
