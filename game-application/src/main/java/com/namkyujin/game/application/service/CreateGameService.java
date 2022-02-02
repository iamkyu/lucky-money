package com.namkyujin.game.application.service;

import com.namkyujin.common.UseCase;
import com.namkyujin.game.application.port.in.CreateGameUseCase;
import com.namkyujin.game.application.port.out.CreateGameAwardPort;
import com.namkyujin.game.application.port.out.CreateGamePort;
import com.namkyujin.game.domain.AwardAmount;
import com.namkyujin.game.domain.Game;
import com.namkyujin.game.domain.GameAwardService;
import com.namkyujin.game.domain.GameRoomUser;
import com.namkyujin.game.domain.GameToken;
import com.namkyujin.game.domain.GameAward;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;

@UseCase
@RequiredArgsConstructor
@Transactional
class CreateGameService implements CreateGameUseCase {
    private final CreateTokenService createTokenService;
    private final CreateGamePort createGamePort;
    private final GameAwardService gameAwardService;
    private final CreateGameAwardPort createGameAwardPort;


    @Override
    public CreatedGamePresentation createGame(GameRoomUser gameUser, CreateGameCommand command) {
        GameToken token = createTokenService.create(gameUser.getGameRoomId());

        Game game = createGamePort.create(
                Game.withoutId(
                        gameUser.getGameRoomId(),
                        token,
                        new AwardAmount(command.getAmount()),
                        command.getTotalWinners(),
                        gameUser.getGameRoomUserId()));

        List<GameAward> gameAwards = gameAwardService.create(game);
        createGameAwardPort.create(game, gameAwards);

        return new CreatedGamePresentation(token);
    }
}
