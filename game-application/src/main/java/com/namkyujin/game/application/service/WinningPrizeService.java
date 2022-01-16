package com.namkyujin.game.application.service;

import com.namkyujin.game.domain.Game;
import com.namkyujin.game.domain.WinningPrize;
import com.namkyujin.game.domain.WinningPrizeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WinningPrizeService {
    private final WinningPrizeRepository winningPrizeRepository;
    private final WinningPrizeRule winningPrizeRule;

    @Transactional
    public List<WinningPrize> createPrize(Game game) {
        List<Integer> prizes = winningPrizeRule.createPrize(game.getAmount(), game.getTotalWinners());
        List<WinningPrize> winningPrizes = new ArrayList<>();
        for (int seq = 0; seq < prizes.size(); seq++) {
            winningPrizes.add(map(game, seq + 1, prizes.get(seq)));
        }

        return winningPrizeRepository.saveAll(winningPrizes);
    }

    public Optional<WinningPrize> getPrize(int sequenceId, long gameId) {
        Optional<WinningPrize> maybeWinningPrize = winningPrizeRepository.findBySequenceAndGameId(sequenceId, gameId);
        return maybeWinningPrize;
    }


    private WinningPrize map(Game game, int sequence, int prize) {
        return WinningPrize.builder()
                .game(game)
                .sequence(sequence)
                .prizeAmount(prize)
                .build();
    }
}
