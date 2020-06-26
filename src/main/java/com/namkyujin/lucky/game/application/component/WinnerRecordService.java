package com.namkyujin.lucky.game.application.component;

import com.namkyujin.lucky.core.domain.*;
import com.namkyujin.lucky.game.model.GameRoomUser;
import com.namkyujin.lucky.game.model.exeception.WinningPrizeNotExistException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class WinnerRecordService {
    private final WinningPrizeService winningPrizeService;
    private final WinnerRepository winnerRepository;

    @Transactional
    public Result record(GameRoomUser gameRoomUser, Game game, Integer sequenceId) {
        WinningPrize winningPrize = winningPrizeService.getPrize(sequenceId, game.getId())
                .orElseThrow(WinningPrizeNotExistException::new);

        winnerRepository.save(map(game, gameRoomUser, winningPrize));
        winningPrize.win();

        return Result.of(winningPrize.getPrizeAmount());
    }

    private Winner map(Game game, GameRoomUser gameRoomUser, WinningPrize winningPrize) {
        return Winner.builder()
                .game(game)
                .prizeAmount(winningPrize.getPrizeAmount())
                .userId(gameRoomUser.getUserId())
                .build();
    }

    @Getter
    @RequiredArgsConstructor(staticName = "of")
    public static class Result {
        private final int prizeAmount;
    }
}
