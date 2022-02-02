package com.namkyujin.game.adapter.persistence;

import com.namkyujin.common.PersistenceAdapter;
import com.namkyujin.game.application.port.out.LoadGameWinnerPort;
import com.namkyujin.game.domain.AwardAmount;
import com.namkyujin.game.domain.Game;
import com.namkyujin.game.domain.GameRoomUserId;
import com.namkyujin.game.domain.GameWinner;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;


@PersistenceAdapter
@RequiredArgsConstructor
class GameWinnerPersistenceAdapter implements LoadGameWinnerPort {
    private final GameWinnerRepository gameWinnerRepository;

    @Override
    public List<GameWinner> load(Game.GameId gameId) {
        List<GameWinnerJpaEntity> gameWinnerJpaEntities = gameWinnerRepository.findAllByGameId(gameId.getValue());

        return gameWinnerJpaEntities.stream()
                .map(winner -> new GameWinner(
                        new GameWinner.WinnerId(winner.getId()),
                        new Game.GameId(winner.getGameId()),
                        new GameRoomUserId(winner.getUserId()),
                        new AwardAmount(winner.getPrizeAmount())
                ))
                .collect(toList());
    }
}
