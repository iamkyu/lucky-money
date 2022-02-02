package com.namkyujin.game.fixme;

/**
 * FIXME 아키텍처 변경과정에서 수정하지 못한 테스트
 */
class TokenServiceTest {
    // private TokenService sut;
    // private TokenRepository tokenRepository;
    // private TokenMetaRepository tokenMetaRepository;
    //
    // @BeforeEach
    // void setUp() {
    //     tokenRepository = mock(TokenRepository.class);
    //     tokenMetaRepository = mock(TokenMetaRepository.class);
    //
    //     sut = new TokenService(tokenRepository, tokenMetaRepository);
    // }
    //
    // @Test
    // void shouldRetryUpdateTokenMeta() {
    //     //given
    //     String roomId = "luck";
    //     TokenMeta tokenMeta = TokenMeta.builder().roomId(roomId).build();
    //     given(tokenMetaRepository.findByRoomId(roomId))
    //             .willReturn(Optional.of(tokenMeta));
    //
    //     tokenMeta.increaseOffset();
    //     given(tokenMetaRepository.save(any()))
    //             .willThrow(new RuntimeException())
    //             .willReturn(tokenMeta);
    //
    //     String expectedToken = "abc";
    //     given(tokenRepository.findById(any())).willReturn(Optional.of(
    //             Token.builder().token(expectedToken).build()));
    //
    //     //when
    //     String result = sut.getToken(roomId);
    //
    //     //then
    //     assertThat(result).isNotEmpty();
    //     verify(tokenMetaRepository, atLeast(2)).save(any());
    // }
}