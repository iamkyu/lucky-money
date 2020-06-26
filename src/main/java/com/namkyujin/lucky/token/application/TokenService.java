package com.namkyujin.lucky.token.application;

import com.namkyujin.lucky.core.domain.token.Token;
import com.namkyujin.lucky.core.domain.token.TokenMeta;
import com.namkyujin.lucky.core.domain.token.TokenMetaRepository;
import com.namkyujin.lucky.core.domain.token.TokenRepository;
import com.namkyujin.lucky.token.model.TokenCreationFailedException;
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
