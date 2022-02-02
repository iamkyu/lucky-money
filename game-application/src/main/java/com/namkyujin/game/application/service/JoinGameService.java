package com.namkyujin.game.application.service;

import com.namkyujin.common.UseCase;
import com.namkyujin.game.application.port.in.JoinGameUseCase;
import com.namkyujin.game.application.port.out.AwardLockPort;
import com.namkyujin.game.application.port.out.LoadGamePort;
import com.namkyujin.game.application.port.out.RecordGameAwardPort;
import com.namkyujin.game.application.service.exception.GameClosedException;
import com.namkyujin.game.application.service.exception.ParticipateRestrictException;
import com.namkyujin.game.domain.Game;
import com.namkyujin.game.domain.GameAward;
import com.namkyujin.game.domain.GameRoomUser;
import com.namkyujin.game.domain.GameToken;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@UseCase
@RequiredArgsConstructor
@Transactional
class JoinGameService implements JoinGameUseCase {
    private final LoadGamePort loadGamePort;
    private final AwardLockPort awardLockPort;
    private final RecordGameAwardPort recordGameAwardPort;

    @Override
    public WinningPrizePresentation join(GameRoomUser gameRoomUser, GameToken token) {
        Game game = loadGamePort.load(token, gameRoomUser.getGameRoomId());

        checkSelfAttendee(game, gameRoomUser);

        GameAward gameAward = getAndLockAward(game);

        recordGameAward(gameRoomUser, game, gameAward);

        return new WinningPrizePresentation(gameAward.getAmount());
    }

    private GameAward getAndLockAward(Game game) {
        try {
            return awardLockPort.getAndLockAward(game);
        } catch (Exception e) {
            throw new GameClosedException();
        }
    }

    private void recordGameAward(GameRoomUser gameRoomUser, Game game, GameAward gameAward) {
        try {
            recordGameAwardPort.record(gameAward.win(), gameRoomUser.getGameRoomUserId());
        } catch (Exception e) {
            awardLockPort.releaseAward(game, gameAward.getSequence());
            throw e;
        }
    }

    private void checkSelfAttendee(Game game, GameRoomUser gameRoomUser) {
        if (game.isMine(gameRoomUser.getGameRoomUserId())) throw ParticipateRestrictException.own();
    }
}
