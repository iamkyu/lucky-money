package com.namkyujin.game.game.application;

import com.namkyujin.game.common.exception.UnauthorizedGameRoomUserException;
import com.namkyujin.game.core.domain.Game;
import com.namkyujin.game.core.domain.Winner;
import com.namkyujin.game.core.domain.WinnerSequenceRepository;
import com.namkyujin.game.core.domain.WinningPrize;
import com.namkyujin.game.game.application.component.GameService;
import com.namkyujin.game.game.application.component.WinnerRecordService;
import com.namkyujin.game.game.application.component.WinningPrizeService;
import com.namkyujin.game.game.model.*;
import com.namkyujin.game.game.model.exeception.GameClosedException;
import com.namkyujin.game.game.model.exeception.GameNotExistException;
import com.namkyujin.game.game.model.exeception.ParticipateRestrictException;
import com.namkyujin.game.token.application.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@RequiredArgsConstructor
public class LuckyService {
    private final TokenService tokenService;
    private final GameService gameService;
    private final WinnerRecordService winnerRecordService;
    private final WinningPrizeService winningPrizeService;
    private final WinnerSequenceRepository winnerSequenceRepository;

    @Transactional
    public CreatedGamePresentation createGame(GameRoomUser gameRoomUser, CreateGameCommand command) {
        String token = tokenService.getToken(gameRoomUser.getRoomId());
        Game game = gameService.createGame(token, gameRoomUser, command);

        List<WinningPrize> prizes = winningPrizeService.createPrize(game);

        winnerSequenceRepository.saveAll(game.getRoomId(), game.getToken(), prizes.stream()
                .map(WinningPrize::getSequence)
                .collect(toList()));

        return CreatedGamePresentation.of(game.getToken());
    }

    @Transactional
    public WinningPrizePresentation getPrize(GameRoomUser gameRoomUser, GameToken token) {
        Game game = gameService.getGame(token, gameRoomUser.getRoomId())
                .orElseThrow(GameNotExistException::new);

        if (game.isMine(gameRoomUser.getUserId())) {
            throw ParticipateRestrictException.own();
        }

        Integer sequenceId = winnerSequenceRepository.getOne(gameRoomUser.getRoomId(), token.get())
                .orElseThrow(GameClosedException::new);

        try {
            return WinningPrizePresentation
                    .of(winnerRecordService.record(gameRoomUser, game, sequenceId).getPrizeAmount());
        } catch (Exception e) {
            log.error("Failed to record winner.", e);
            winnerSequenceRepository.release(gameRoomUser.getRoomId(), token.get(), sequenceId);
            throw ParticipateRestrictException.already();
        }
    }

    @Transactional
    public GameStatusPresentation getGameStatus(GameRoomUser gameRoomUser, GameToken token) {
        LocalDateTime baseDateTime = LocalDateTime.now().minus(Game.getLookUpAbleDuration());
        Game game = gameService.getLookUpAbleGame(token, baseDateTime)
                .orElseThrow(() -> new GameNotExistException(token.get()));

        if (!game.isMine(gameRoomUser.getUserId())) {
            throw new UnauthorizedGameRoomUserException();
        }

        return map(game);
    }


    private GameStatusPresentation map(Game game) {
        int completeAmount = 0;
        List<GameStatusPresentation.Winner> winners = new ArrayList<>();
        for (Winner winner : game.getWinners()) {
            winners.add(GameStatusPresentation.Winner.of(winner.getPrizeAmount(), winner.getUserId()));
            completeAmount += winner.getPrizeAmount();
        }

        GameStatusPresentation presentation = GameStatusPresentation
                .of(game.getCreatedAt(), game.getAmount(), completeAmount);
        presentation.addAll(winners);
        return presentation;
    }
}
