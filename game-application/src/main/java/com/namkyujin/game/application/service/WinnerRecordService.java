package com.namkyujin.game.application.service;

import com.namkyujin.game.domain.Game;
import com.namkyujin.game.domain.Winner;
import com.namkyujin.game.domain.WinnerRepository;
import com.namkyujin.game.domain.WinningPrize;
import com.namkyujin.game.model.GameRoomUser;
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
