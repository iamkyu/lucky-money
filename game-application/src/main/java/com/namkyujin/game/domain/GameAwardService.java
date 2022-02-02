package com.namkyujin.game.domain;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class GameAwardService {
    private final GameAwardRule gameAwardRule;

    public List<GameAward> create(Game game) {
        List<Long> awards = gameAwardRule.createAward(game.getAmount(), game.getTotalWinners());

        return mapToGameAwards(game, awards);
    }

    private List<GameAward> mapToGameAwards(Game game, List<Long> awards) {
        List<GameAward> gameAwards = new ArrayList<>();

        for (int seq = 0; seq < awards.size(); seq++) {
            gameAwards.add(mapToGameAward(game, seq + 1, awards.get(seq)));
        }

        return gameAwards;
    }

    private GameAward mapToGameAward(Game game, int sequence, long prize) {
        return GameAward.withoutId(Long.valueOf(sequence), game.getId(), new AwardAmount(prize));
    }
}
