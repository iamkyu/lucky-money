package com.namkyujin.game.token.application;

import com.namkyujin.game.core.domain.token.Token;
import com.namkyujin.game.core.domain.token.TokenMeta;
import com.namkyujin.game.core.domain.token.TokenMetaRepository;
import com.namkyujin.game.core.domain.token.TokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class TokenServiceTest {
    private TokenService sut;
    private TokenRepository tokenRepository;
    private TokenMetaRepository tokenMetaRepository;

    @BeforeEach
    void setUp() {
        tokenRepository = mock(TokenRepository.class);
        tokenMetaRepository = mock(TokenMetaRepository.class);

        sut = new TokenService(tokenRepository, tokenMetaRepository);
    }

    @Test
    void shouldRetryUpdateTokenMeta() {
        //given
        String roomId = "luck";
        TokenMeta tokenMeta = TokenMeta.builder().roomId(roomId).build();
        given(tokenMetaRepository.findByRoomId(roomId))
                .willReturn(Optional.of(tokenMeta));

        tokenMeta.increaseOffset();
        given(tokenMetaRepository.save(any()))
                .willThrow(new RuntimeException())
                .willReturn(tokenMeta);

        String expectedToken = "abc";
        given(tokenRepository.findById(any())).willReturn(Optional.of(
                Token.builder().token(expectedToken).build()));

        //when
        String result = sut.getToken(roomId);

        //then
        assertThat(result).isNotEmpty();
        verify(tokenMetaRepository, atLeast(2)).save(any());
    }
}