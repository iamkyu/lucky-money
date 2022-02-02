package com.namkyujin.game.adapter.persistence;

import com.namkyujin.common.PersistenceAdapter;
import com.namkyujin.game.application.port.out.CreateTokenPort;
import com.namkyujin.game.application.port.out.LoadTokenPort;
import com.namkyujin.game.domain.GameRoomId;
import com.namkyujin.game.domain.GameToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@PersistenceAdapter
@RequiredArgsConstructor
@Slf4j
class TokenPersistenceAdapter implements CreateTokenPort, LoadTokenPort {
    private static final int MAX_RETRY = 3;

    private final TokenRepository tokenRepository;
    private final TokenMetaRepository tokenMetaRepository;

    @Override
    public void create(List<GameToken> tokens) {
        List<TokenJpaEntity> tokenJpaEntities = tokens.stream()
                .map(token -> TokenJpaEntity.withoutId(token.getValue()))
                .collect(toList());

        tokenRepository.saveAll(tokenJpaEntities);
    }

    @Override
    public GameToken loadToken(GameRoomId gameRoomId) {
        TokenMetaJpaEntity tokenMeta = getTokenMeta(gameRoomId.getValue());

        TokenJpaEntity token = tokenRepository
                .findById(tokenMeta.getIdOffSet())
                .orElseThrow(EntityNotFoundException::new);

        return mapToDomainEntity(token);
    }

    private TokenMetaJpaEntity getTokenMeta(String roomId) {
        for (int index = 0; index < MAX_RETRY; index++) {
            try {
                TokenMetaJpaEntity tokenMeta = tokenMetaRepository.findByRoomId(roomId)
                        .orElseGet(() -> new TokenMetaJpaEntity(roomId));

                tokenMeta.increaseOffset();

                return tokenMetaRepository.save(tokenMeta);
            } catch (Exception e) {
                // retry
            }
        }

        throw new NoResultException();
    }

    private GameToken mapToDomainEntity(TokenJpaEntity token) {
        return new GameToken(token.getToken());
    }
}
