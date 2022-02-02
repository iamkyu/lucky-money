package com.namkyujin.game.adapter.persistence;

import com.namkyujin.common.PersistenceAdapter;
import com.namkyujin.game.application.port.out.CreateGamePort;
import com.namkyujin.game.application.port.out.LoadGamePort;
import com.namkyujin.game.domain.AwardAmount;
import com.namkyujin.game.domain.Game;
import com.namkyujin.game.domain.GameRoomId;
import com.namkyujin.game.domain.GameRoomUserId;
import com.namkyujin.game.domain.GameToken;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityNotFoundException;

@PersistenceAdapter
@RequiredArgsConstructor
class GamePersistenceAdapter implements CreateGamePort, LoadGamePort {
    private final GameRepository gameRepository;

    public Game create(Game game) {
        GameJpaEntity entity = new GameJpaEntity(
                game.getRoomId().getValue(),
                game.getToken().getValue(),
                game.getAmount().getValue(),
                game.getTotalWinners(),
                game.getCreatedBy().getValue());

        GameJpaEntity saved = gameRepository.save(entity);

        return Game.withId(
                new Game.GameId(saved.getId()),
                new GameRoomId(saved.getRoomId()),
                new GameToken(saved.getToken()),
                new AwardAmount(saved.getAmount()),
                saved.getTotalWinners(),
                new GameRoomUserId(saved.getCreatedBy()),
                saved.getCreatedAt()
        );
    }

    @Override
    public Game load(GameToken token, GameRoomId gameRoomId) {
        GameJpaEntity entity = gameRepository
                .findByTokenAndRoomId(token.getValue(), gameRoomId.getValue())
                .orElseThrow(EntityNotFoundException::new);

        return Game.withId(
                new Game.GameId(entity.getId()),
                new GameRoomId(entity.getRoomId()),
                new GameToken(entity.getToken()),
                new AwardAmount(entity.getAmount()),
                entity.getTotalWinners(),
                new GameRoomUserId(entity.getCreatedBy()),
                entity.getCreatedAt()
        );
    }
}
