package com.namkyujin.game.fixme;

/**
 * FIXME 아키텍처 변경과정에서 수정하지 못한 테스트
 */
class GameWinnerRecordServiceTest {
    // private static final GameRoomUser USER = GameRoomUser.builder().roomId("lucky").userId(1L).build();
    // private static final Game GAME = Game.builder()
    //         .token("foo").amount(1000).totalWinners(3).roomId(USER.getGameRoomId()).createdBy(USER.getGameRoomUserId())
    //         .build();
    //
    // private WinnerRecordService sut;
    // private WinningPrizeService winningPrizeService;
    // private WinnerRepository winnerRepository;
    //
    // @BeforeEach
    // void setUp() {
    //     ReflectionTestUtils.setField(GAME, "id", 1L);
    //     winningPrizeService = mock(WinningPrizeService.class);
    //     winnerRepository = mock(WinnerRepository.class);
    //
    //     sut = new WinnerRecordService(winningPrizeService, winnerRepository);
    // }
    //
    //
    // @Test
    // void record_shouldRecord() {
    //     //given
    //     int sequenceId = 1;
    //     int prizeAmount = 1000;
    //     GameAward gameAward = GameAward.builder()
    //             .game(GAME)
    //             .sequence(sequenceId)
    //             .prizeAmount(prizeAmount)
    //             .build();
    //
    //     given(winningPrizeService.getPrize(eq(sequenceId), eq(GAME.getId())))
    //             .willReturn(Optional.of(gameAward));
    //
    //     assertThat(gameAward.getStatus()).isEqualTo(WinningPrizeStatus.NO_WINNER);
    //
    //     //when
    //     WinnerRecordService.Result result = sut.record(USER, GAME, sequenceId);
    //
    //     //then
    //     assertThat(gameAward.getStatus()).isEqualTo(WinningPrizeStatus.WIN);
    //     assertThat(result.getPrizeAmount()).isEqualTo(prizeAmount);
    // }
    //
    // @Test
    // void record_shouldOccurExceptionIfPrizeNotExist() {
    //     //given
    //     int sequenceId = 1;
    //     given(winningPrizeService.getPrize(eq(sequenceId), eq(GAME.getId())))
    //             .willReturn(Optional.empty());
    //
    //     //when
    //     Throwable result = catchThrowable(() -> sut.record(USER, GAME, sequenceId));
    //
    //     //then
    //     assertThat(result).isInstanceOf(WinningPrizeNotExistException.class);
    // }
}