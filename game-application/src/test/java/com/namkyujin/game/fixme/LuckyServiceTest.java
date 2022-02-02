package com.namkyujin.game.fixme;

/**
 * FIXME 아키텍처 변경과정에서 수정하지 못한 테스트
 */
class LuckyServiceTest {
    // private static final GameRoomUser USER = GameRoomUser.builder().roomId("lucky").userId(1L).build();
    // public static final GameToken TOKEN = GameToken.of("foo");
    //
    // private LuckyService sut;
    // private TokenService tokenService;
    // private GameService gameService;
    // private WinnerRecordService winnerRecordService;
    // private GameAwardService gameAwardService;
    // private TestInMemoryWinnerSequenceRepository winnerSequenceRepository;
    //
    // @BeforeEach
    // void setUp() {
    //     tokenService = mock(TokenService.class);
    //     gameService = mock(GameService.class);
    //     winnerRecordService = mock(WinnerRecordService.class);
    //     gameAwardService = mock(GameAwardService.class);
    //     winnerSequenceRepository = new TestInMemoryWinnerSequenceRepository();
    //     sut = new LuckyService(tokenService, gameService, winnerRecordService, gameAwardService, winnerSequenceRepository);
    // }
    //
    // @Test
    // void createGame_shouldCreateGame() {
    //     //given
    //     int expectAmount = 1000;
    //     int expectTotalWinners = 3;
    //     CreateGameUseCase.CreateGameCommand command = CreateGameUseCase.CreateGameCommand.of(expectAmount, expectTotalWinners);
    //
    //     given(tokenService.getToken(eq(USER.getGameRoomId()))).willReturn(TOKEN.get());
    //     Game game = makeGame(TOKEN, expectAmount, expectTotalWinners, USER.getGameRoomUserId());
    //     given(gameService.createGame(eq(TOKEN.get()), eq(USER), eq(command))).willReturn(game);
    //
    //     List<GameAward> prizes = new ArrayList<>();
    //     prizes.add(GameAward.builder().game(game).sequence(1).prizeAmount(1).build());
    //     prizes.add(GameAward.builder().game(game).sequence(2).prizeAmount(2).build());
    //     given(gameAwardService.createPrize(eq(game))).willReturn(prizes);
    //
    //     //when
    //     CreateGameUseCase.CreatedGamePresentation result = sut.createGame(USER, command);
    //
    //     //then
    //     assertThat(result.getToken()).isEqualTo(TOKEN.get());
    //     assertThat(winnerSequenceRepository.isExsistSequence(USER.getGameRoomId(), TOKEN.get(), 1)).isTrue();
    //     assertThat(winnerSequenceRepository.isExsistSequence(USER.getGameRoomId(), TOKEN.get(), 2)).isTrue();
    //
    //     verify(tokenService).getToken(eq(USER.getGameRoomId()));
    //     verify(gameService).createGame(eq(TOKEN.get()), eq(USER), eq(command));
    //     verify(gameAwardService).createPrize(eq(game));
    // }
    //
    //
    // @Test
    // void getPrize_shouldGetPrize() {
    //     //given
    //     int expectedAmount = 1000;
    //     int expectedTotalWinners = 3;
    //     Game game = makeGame(TOKEN, expectedAmount, expectedTotalWinners, USER.getGameRoomUserId() + 1);
    //
    //     int sequenceId = 1;
    //     List<Integer> sequences = makeSequences(sequenceId);
    //     winnerSequenceRepository.saveAll(game.getRoomId(), game.getToken(), sequences);
    //
    //     given(gameService.getGame(eq(TOKEN), any())).willReturn(Optional.of(game));
    //     given(winnerRecordService.record(any(), any(), anyInt())).willReturn(WinnerRecordService.Result.of(expectedAmount));
    //
    //     //when
    //     JoinGameUseCase.WinningPrizePresentation result = sut.getPrize(USER, TOKEN);
    //
    //     //then
    //     assertThat(result.getPrizeAmount()).isEqualTo(expectedAmount);
    //     assertThat(winnerSequenceRepository.isExsistSequence(USER.getGameRoomId(), TOKEN.get(), sequenceId)).isFalse();
    // }
    //
    // @Test
    // void getPrize_shouldReleaseSequenceIfWinnerRecordFailed() {
    //     //given
    //     int expectedAmount = 1000;
    //     int expectedTotalWinners = 3;
    //     Game game = makeGame(TOKEN, expectedAmount, expectedTotalWinners, USER.getGameRoomUserId() + 1);
    //
    //     int sequenceId = 1;
    //     List<Integer> sequences = makeSequences(sequenceId);
    //     winnerSequenceRepository.saveAll(game.getRoomId(), game.getToken(), sequences);
    //
    //     given(gameService.getGame(eq(TOKEN), any())).willReturn(Optional.of(game));
    //     given(winnerRecordService.record(any(), any(), anyInt())).willThrow(new RuntimeException("test"));
    //
    //     //when
    //     Throwable result = catchThrowable(() -> sut.getPrize(USER, TOKEN));
    //
    //     //then
    //     assertThat(result).isInstanceOf(IllegalStateException.class);
    //     assertThat(winnerSequenceRepository.isExsistSequence(USER.getGameRoomId(), TOKEN.get(), sequenceId)).isTrue();
    // }
    //
    // @Test
    // void getPrize_shouldOccurExceptionIfGameNotExist() {
    //     //given
    //     given(gameService.getLookUpAbleGame(eq(TOKEN), any()))
    //             .willReturn(Optional.empty());
    //
    //     //when
    //     Throwable result = catchThrowable(() -> sut.getPrize(USER, TOKEN));
    //
    //     //then
    //     assertThat(result).isInstanceOf(GameNotExistException.class);
    // }
    //
    // @Test
    // void getPrize_shouldOccurExceptionIfUserIsCreator() {
    //     //given
    //     given(gameService.getGame(eq(TOKEN), eq(USER.getGameRoomId())))
    //             .willReturn(Optional.of(makeGame(TOKEN, 1, 1, USER.getGameRoomUserId())));
    //
    //     //when
    //     Throwable result = catchThrowable(() -> sut.getPrize(USER, TOKEN));
    //
    //     //then
    //     assertThat(result).isInstanceOf(ParticipateRestrictException.class);
    // }
    //
    //
    // @Test
    // void getGameStatus_shouldOccurExceptionIfGameNotExist() {
    //     //given
    //     given(gameService.getLookUpAbleGame(eq(TOKEN), any()))
    //             .willReturn(Optional.empty());
    //
    //     //when
    //     Throwable result = catchThrowable(() -> sut.getGameStatus(USER, TOKEN));
    //
    //     //then
    //     assertThat(result).isInstanceOf(GameNotExistException.class);
    // }
    //
    // @Test
    // void getGameStatus_shouldOccurExceptionIfUserIsNotCreator() {
    //     //given
    //     String token = "foo";
    //     long otherUserId = 99L;
    //     given(gameService.getLookUpAbleGame(eq(TOKEN), any()))
    //             .willReturn(Optional.of(makeGame(TOKEN, 1, 1, otherUserId)));
    //
    //     //when
    //     Throwable result = catchThrowable(() -> sut.getGameStatus(USER, TOKEN));
    //
    //     //then
    //     assertThat(result).isInstanceOf(UnauthorizedGameRoomUserException.class);
    // }
    //
    // @Test
    // void getGameStatus_shouldGetGameStatus() {
    //     //given
    //     int expectedAmount = 1000;
    //     int expectedTotalWinners = 3;
    //     Game game = makeGame(TOKEN, expectedAmount, expectedTotalWinners, USER.getGameRoomUserId());
    //
    //     int expectedPrize = 100;
    //     GameWinner.WinnerBuilder winnerBuilder = GameWinner.builder().prizeAmount(expectedPrize);
    //     List<GameWinner> gameWinners = Arrays.asList(
    //             winnerBuilder.userId(USER.getGameRoomUserId() + 1).build(),
    //             winnerBuilder.userId(USER.getGameRoomUserId() + 2).build()
    //     );
    //     game.addWinners(gameWinners);
    //
    //     given(gameService.getLookUpAbleGame(eq(TOKEN), any()))
    //             .willReturn(Optional.of(game));
    //
    //     //when
    //     GetGameStatusQuery.GameStatusPresentation gameStatus = sut.getGameStatus(USER, TOKEN);
    //
    //     //then
    //     assertThat(gameStatus.getAmount()).isEqualTo(expectedAmount);
    //     assertThat(gameStatus.getCompleteAmount()).isEqualTo(expectedPrize * gameWinners.size());
    //     assertThat(gameStatus.getWinners().size()).isEqualTo(gameWinners.size());
    // }
    //
    // private Game makeGame(GameToken token, int amount, int totalWinners, Long userId) {
    //     return Game.builder()
    //             .token(token.get())
    //             .amount(amount)
    //             .totalWinners(totalWinners)
    //             .roomId(USER.getGameRoomId())
    //             .createdBy(userId)
    //             .build();
    // }
    //
    // private List<Integer> makeSequences(int... sequenceIds) {
    //     List<Integer> sequences = new ArrayList<>();
    //     Arrays.stream(sequenceIds).forEach(sequences::add);
    //     return sequences;
    // }
}