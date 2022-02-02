package com.namkyujin.game.adapter.persistence;

import com.namkyujin.common.PersistenceAdapter;
import com.namkyujin.game.application.port.out.AwardLockPort;
import com.namkyujin.game.application.port.out.CreateGameAwardPort;
import com.namkyujin.game.application.port.out.RecordGameAwardPort;
import com.namkyujin.game.application.service.exception.ParticipateRestrictException;
import com.namkyujin.game.domain.AwardAmount;
import com.namkyujin.game.domain.Game;
import com.namkyujin.game.domain.GameAward;
import com.namkyujin.game.domain.GameRoomUserId;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@PersistenceAdapter
@RequiredArgsConstructor
class GameAwardPersistenceAdapter implements CreateGameAwardPort, AwardLockPort, RecordGameAwardPort {
    private final GameAwardLockRepository gameAwardLockRepository;
    private final GameAwardRepository gameAwardRepository;
    private final GameWinnerRepository gameWinnerRepository;

    @Override
    public void create(Game game, List<GameAward> gameAwards) {
        List<GameAwardJpaEntity> entities = gameAwards.stream()
                .map(this::mapToGameAwardJpaEntity)
                .collect(toList());

        gameAwardRepository.saveAll(entities);

        List<Long> sequences = gameAwards.stream()
                .map(GameAward::getSequence)
                .collect(toList());

        gameAwardLockRepository.saveAll(game.getRoomId().getValue(), game.getToken().getValue(), sequences);
    }

    @Override
    public GameAward getAndLockAward(Game game) {
        Long sequenceId = gameAwardLockRepository
                .lock(game.getRoomId().getValue(), game.getToken().getValue())
                .orElseThrow(EntityNotFoundException::new);

        GameAwardJpaEntity gameAwardJpaEntity = gameAwardRepository
                .findBySequenceAndGameId(sequenceId, game.getId().getValue())
                .orElseThrow(EntityNotFoundException::new);

        return new GameAward(
                new GameAward.GameAwardId(gameAwardJpaEntity.getId()),
                gameAwardJpaEntity.getSequence(),
                new Game.GameId(gameAwardJpaEntity.getGameId()),
                new AwardAmount(gameAwardJpaEntity.getPrizeAmount()),
                GameAward.Status.valueOf(gameAwardJpaEntity.getStatus()));
    }

    @Override
    public void releaseAward(Game game, Long sequence) {
        gameAwardLockRepository.release(
                game.getRoomId().getValue(),
                game.getToken().getValue(),
                sequence);
    }

    @Override
    public void record(GameAward gameAward, GameRoomUserId gameRoomUserId) {
        gameWinnerRepository.findByGameIdAndUserId(gameAward.getGameId().getValue(), gameRoomUserId.getValue())
                .ifPresent(found -> {
                    throw ParticipateRestrictException.already();
                });

        GameWinnerJpaEntity gameWinnerJpaEntity = new GameWinnerJpaEntity(
                gameAward.getGameId().getValue(),
                gameRoomUserId.getValue(),
                gameAward.getAmount().getValue());

        gameWinnerRepository.save(gameWinnerJpaEntity);

        gameAwardRepository.save(mapToGameAwardJpaEntity(gameAward));
    }

    private GameAwardJpaEntity mapToGameAwardJpaEntity(GameAward gameAward) {
        return new GameAwardJpaEntity(
                gameAward.getGameId().getValue(),
                gameAward.getSequence(),
                gameAward.getAmount().getValue(),
                gameAward.getStatus().name());
    }
}
