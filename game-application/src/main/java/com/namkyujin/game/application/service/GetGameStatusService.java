package com.namkyujin.game.application.service;

import com.namkyujin.common.QueryService;
import com.namkyujin.game.application.port.in.GetGameStatusQuery;
import com.namkyujin.game.application.port.out.LoadGamePort;
import com.namkyujin.game.application.port.out.LoadGameWinnerPort;
import com.namkyujin.game.application.service.exception.GameNotExistException;
import com.namkyujin.game.application.service.exception.UnauthorizedGameRoomUserException;
import com.namkyujin.game.domain.AwardAmount;
import com.namkyujin.game.domain.Game;
import com.namkyujin.game.domain.GameRoomUser;
import com.namkyujin.game.domain.GameToken;
import com.namkyujin.game.domain.GameWinner;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@QueryService
@Transactional
@RequiredArgsConstructor
class GetGameStatusService implements GetGameStatusQuery {
    private static final Duration LOOK_UP_ABLE_DURATION = Duration.ofDays(7);

    private final LoadGamePort loadGamePort;
    private final LoadGameWinnerPort loadGameWinnerPort;

    @Override
    public GameStatusPresentation getStatus(GameRoomUser gameRoomUser, GameToken token) {
        Game game = loadGame(gameRoomUser, token);

        checkLookUpAble(game);

        checkGameOwner(game, gameRoomUser);

        List<GameWinner> winners = loadGameWinnerPort.load(game.getId());

        return mapToGameStatusPresentation(game, winners);
    }

    private Game loadGame(GameRoomUser gameRoomUser, GameToken token) {
        try {
            return loadGamePort.load(token, gameRoomUser.getGameRoomId());
        } catch (Exception e) {
            throw new GameNotExistException();
        }
    }

    private void checkLookUpAble(Game game) {
        LocalDateTime lookUpAbleDeadLine = game.getCreatedAt().plus(LOOK_UP_ABLE_DURATION);
        if (LocalDateTime.now().isAfter(lookUpAbleDeadLine)) {
            throw new GameNotExistException(game.getId());
        }
    }

    private void checkGameOwner(Game game, GameRoomUser gameRoomUser) {
        if (!game.isMine(gameRoomUser.getGameRoomUserId())) throw new UnauthorizedGameRoomUserException();
    }

    private GameStatusPresentation mapToGameStatusPresentation(Game game, List<GameWinner> winners) {
        AwardAmount completeAmount = new AwardAmount(0L);
        List<GameStatusPresentation.Winner> winnerPresentation = new ArrayList<>();

        for (GameWinner each : winners) {
            winnerPresentation.add(new GameStatusPresentation.Winner(each.getAmount(), each.getGameRoomUserId()));
            completeAmount = completeAmount.add(each.getAmount());
        }

        return new GameStatusPresentation(
                game.getCreatedAt(),
                game.getAmount(),
                completeAmount,
                winnerPresentation);
    }
}
