package com.namkyujin.game.application.service;

import com.namkyujin.game.domain.Token;
import com.namkyujin.game.domain.TokenMeta;
import com.namkyujin.game.domain.TokenMetaRepository;
import com.namkyujin.game.domain.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {
    protected static final int MAX_RETRY = 3;

    private final TokenRepository tokenRepository;
    private final TokenMetaRepository tokenMetaRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String getToken(String roomId) {
        TokenMeta tokenMeta = getTokenMeta(roomId);
        Token token = tokenRepository.findById(tokenMeta.getIdOffSet())
                .orElseThrow(IllegalStateException::new);

        return token.getToken();
    }

    private TokenMeta getTokenMeta(String roomId) {

        for (int index = 0; index < MAX_RETRY; index++) {
            try {
                TokenMeta tokenMeta = tokenMetaRepository.findByRoomId(roomId)
                        .orElseGet(() -> TokenMeta.builder().roomId(roomId).build());
                tokenMeta.increaseOffset();

                return tokenMetaRepository.save(tokenMeta);
            } catch (Exception e) {
                log.error("Failed to update offset. {}. Cause by {}", e.getMessage(), e);
            }
        }

        throw new TokenCreationFailedException();
    }
}
