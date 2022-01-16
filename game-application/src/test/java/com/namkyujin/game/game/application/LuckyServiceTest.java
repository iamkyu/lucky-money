package com.namkyujin.game.game.application;

import com.namkyujin.game.common.exception.UnauthorizedGameRoomUserException;
import com.namkyujin.game.core.domain.Game;
import com.namkyujin.game.core.domain.Winner;
import com.namkyujin.game.core.domain.WinningPrize;
import com.namkyujin.game.game.application.component.GameService;
import com.namkyujin.game.game.application.component.WinnerRecordService;
import com.namkyujin.game.game.application.component.WinningPrizeService;
import com.namkyujin.game.game.model.CreatedGamePresentation;
import com.namkyujin.game.game.model.GameRoomUser;
import com.namkyujin.game.game.model.GameStatusPresentation;
import com.namkyujin.game.game.model.GameToken;
import com.namkyujin.game.game.model.exeception.GameNotExistException;
import com.namkyujin.game.game.model.exeception.ParticipateRestrictException;
import com.namkyujin.game.support.TestInMemoryWinnerSequenceRepository;
import com.namkyujin.game.token.application.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import com.namkyujin.game.game.model.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class LuckyServiceTest {
    private static final GameRoomUser USER = GameRoomUser.builder().roomId("lucky").userId(1L).build();
    public static final GameToken TOKEN = GameToken.of("foo");

    private LuckyService sut;
    private TokenService tokenService;
    private GameService gameService;
    private WinnerRecordService winnerRecordService;
    private WinningPrizeService winningPrizeService;
    private TestInMemoryWinnerSequenceRepository winnerSequenceRepository;

    @BeforeEach
    void setUp() {
        tokenService = mock(TokenService.class);
        gameService = mock(GameService.class);
        winnerRecordService = mock(WinnerRecordService.class);
        winningPrizeService = mock(WinningPrizeService.class);
        winnerSequenceRepository = new TestInMemoryWinnerSequenceRepository();
        sut = new LuckyService(tokenService, gameService, winnerRecordService, winningPrizeService, winnerSequenceRepository);
    }

    @Test
    void createGame_shouldCreateGame() {
        //given
        int expectAmount = 1000;
        int expectTotalWinners = 3;
        CreateGameCommand command = CreateGameCommand.of(expectAmount, expectTotalWinners);

        given(tokenService.getToken(eq(USER.getRoomId()))).willReturn(TOKEN.get());
        Game game = makeGame(TOKEN, expectAmount, expectTotalWinners, USER.getUserId());
        given(gameService.createGame(eq(TOKEN.get()), eq(USER), eq(command))).willReturn(game);

        List<WinningPrize> prizes = new ArrayList<>();
        prizes.add(WinningPrize.builder().game(game).sequence(1).prizeAmount(1).build());
        prizes.add(WinningPrize.builder().game(game).sequence(2).prizeAmount(2).build());
        given(winningPrizeService.createPrize(eq(game))).willReturn(prizes);

        //when
        CreatedGamePresentation result = sut.createGame(USER, command);

        //then
        assertThat(result.getToken()).isEqualTo(TOKEN.get());
        assertThat(winnerSequenceRepository.isExsistSequence(USER.getRoomId(), TOKEN.get(), 1)).isTrue();
        assertThat(winnerSequenceRepository.isExsistSequence(USER.getRoomId(), TOKEN.get(), 2)).isTrue();

        verify(tokenService).getToken(eq(USER.getRoomId()));
        verify(gameService).createGame(eq(TOKEN.get()), eq(USER), eq(command));
        verify(winningPrizeService).createPrize(eq(game));
    }


    @Test
    void getPrize_shouldGetPrize() {
        //given
        int expectedAmount = 1000;
        int expectedTotalWinners = 3;
        Game game = makeGame(TOKEN, expectedAmount, expectedTotalWinners, USER.getUserId() + 1);

        int sequenceId = 1;
        List<Integer> sequences = makeSequences(sequenceId);
        winnerSequenceRepository.saveAll(game.getRoomId(), game.getToken(), sequences);

        given(gameService.getGame(eq(TOKEN), any())).willReturn(Optional.of(game));
        given(winnerRecordService.record(any(), any(), anyInt())).willReturn(WinnerRecordService.Result.of(expectedAmount));

        //when
        WinningPrizePresentation result = sut.getPrize(USER, TOKEN);

        //then
        assertThat(result.getPrizeAmount()).isEqualTo(expectedAmount);
        assertThat(winnerSequenceRepository.isExsistSequence(USER.getRoomId(), TOKEN.get(), sequenceId)).isFalse();
    }

    @Test
    void getPrize_shouldReleaseSequenceIfWinnerRecordFailed() {
        //given
        int expectedAmount = 1000;
        int expectedTotalWinners = 3;
        Game game = makeGame(TOKEN, expectedAmount, expectedTotalWinners, USER.getUserId() + 1);

        int sequenceId = 1;
        List<Integer> sequences = makeSequences(sequenceId);
        winnerSequenceRepository.saveAll(game.getRoomId(), game.getToken(), sequences);

        given(gameService.getGame(eq(TOKEN), any())).willReturn(Optional.of(game));
        given(winnerRecordService.record(any(), any(), anyInt())).willThrow(new RuntimeException("test"));

        //when
        Throwable result = catchThrowable(() -> sut.getPrize(USER, TOKEN));

        //then
        assertThat(result).isInstanceOf(IllegalStateException.class);
        assertThat(winnerSequenceRepository.isExsistSequence(USER.getRoomId(), TOKEN.get(), sequenceId)).isTrue();
    }

    @Test
    void getPrize_shouldOccurExceptionIfGameNotExist() {
        //given
        given(gameService.getLookUpAbleGame(eq(TOKEN), any()))
                .willReturn(Optional.empty());

        //when
        Throwable result = catchThrowable(() -> sut.getPrize(USER, TOKEN));

        //then
        assertThat(result).isInstanceOf(GameNotExistException.class);
    }

    @Test
    void getPrize_shouldOccurExceptionIfUserIsCreator() {
        //given
        given(gameService.getGame(eq(TOKEN), eq(USER.getRoomId())))
                .willReturn(Optional.of(makeGame(TOKEN, 1, 1, USER.getUserId())));

        //when
        Throwable result = catchThrowable(() -> sut.getPrize(USER, TOKEN));

        //then
        assertThat(result).isInstanceOf(ParticipateRestrictException.class);
    }


    @Test
    void getGameStatus_shouldOccurExceptionIfGameNotExist() {
        //given
        given(gameService.getLookUpAbleGame(eq(TOKEN), any()))
                .willReturn(Optional.empty());

        //when
        Throwable result = catchThrowable(() -> sut.getGameStatus(USER, TOKEN));

        //then
        assertThat(result).isInstanceOf(GameNotExistException.class);
    }

    @Test
    void getGameStatus_shouldOccurExceptionIfUserIsNotCreator() {
        //given
        String token = "foo";
        long otherUserId = 99L;
        given(gameService.getLookUpAbleGame(eq(TOKEN), any()))
                .willReturn(Optional.of(makeGame(TOKEN, 1, 1, otherUserId)));

        //when
        Throwable result = catchThrowable(() -> sut.getGameStatus(USER, TOKEN));

        //then
        assertThat(result).isInstanceOf(UnauthorizedGameRoomUserException.class);
    }

    @Test
    void getGameStatus_shouldGetGameStatus() {
        //given
        int expectedAmount = 1000;
        int expectedTotalWinners = 3;
        Game game = makeGame(TOKEN, expectedAmount, expectedTotalWinners, USER.getUserId());

        int expectedPrize = 100;
        Winner.WinnerBuilder winnerBuilder = Winner.builder().prizeAmount(expectedPrize);
        List<Winner> winners = Arrays.asList(
                winnerBuilder.userId(USER.getUserId() + 1).build(),
                winnerBuilder.userId(USER.getUserId() + 2).build()
        );
        game.addWinners(winners);

        given(gameService.getLookUpAbleGame(eq(TOKEN), any()))
                .willReturn(Optional.of(game));

        //when
        GameStatusPresentation gameStatus = sut.getGameStatus(USER, TOKEN);

        //then
        assertThat(gameStatus.getAmount()).isEqualTo(expectedAmount);
        assertThat(gameStatus.getCompleteAmount()).isEqualTo(expectedPrize * winners.size());
        assertThat(gameStatus.getWinners().size()).isEqualTo(winners.size());
    }

    private Game makeGame(GameToken token, int amount, int totalWinners, Long userId) {
        return Game.builder()
                .token(token.get())
                .amount(amount)
                .totalWinners(totalWinners)
                .roomId(USER.getRoomId())
                .createdBy(userId)
                .build();
    }

    private List<Integer> makeSequences(int... sequenceIds) {
        List<Integer> sequences = new ArrayList<>();
        Arrays.stream(sequenceIds).forEach(sequences::add);
        return sequences;
    }
}