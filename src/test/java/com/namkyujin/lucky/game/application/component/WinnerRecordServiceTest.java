package com.namkyujin.lucky.game.application.component;

import com.namkyujin.lucky.core.domain.Game;
import com.namkyujin.lucky.core.domain.WinnerRepository;
import com.namkyujin.lucky.core.domain.WinningPrize;
import com.namkyujin.lucky.core.domain.WinningPrizeStatus;
import com.namkyujin.lucky.game.model.GameRoomUser;
import com.namkyujin.lucky.game.model.exeception.WinningPrizeNotExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class WinnerRecordServiceTest {
    private static final GameRoomUser USER = GameRoomUser.builder().roomId("lucky").userId(1L).build();
    private static final Game GAME = Game.builder()
            .token("foo").amount(1000).totalWinners(3).roomId(USER.getRoomId()).createdBy(USER.getUserId())
            .build();

    private WinnerRecordService sut;
    private WinningPrizeService winningPrizeService;
    private WinnerRepository winnerRepository;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(GAME, "id", 1L);
        winningPrizeService = mock(WinningPrizeService.class);
        winnerRepository = mock(WinnerRepository.class);

        sut = new WinnerRecordService(winningPrizeService, winnerRepository);
    }


    @Test
    void record_shouldRecord() {
        //given
        int sequenceId = 1;
        int prizeAmount = 1000;
        WinningPrize winningPrize = WinningPrize.builder()
                .game(GAME)
                .sequence(sequenceId)
                .prizeAmount(prizeAmount)
                .build();

        given(winningPrizeService.getPrize(eq(sequenceId), eq(GAME.getId())))
                .willReturn(Optional.of(winningPrize));

        assertThat(winningPrize.getStatus()).isEqualTo(WinningPrizeStatus.NO_WINNER);

        //when
        WinnerRecordService.Result result = sut.record(USER, GAME, sequenceId);

        //then
        assertThat(winningPrize.getStatus()).isEqualTo(WinningPrizeStatus.WIN);
        assertThat(result.getPrizeAmount()).isEqualTo(prizeAmount);
    }

    @Test
    void record_shouldOccurExceptionIfPrizeNotExist() {
        //given
        int sequenceId = 1;
        given(winningPrizeService.getPrize(eq(sequenceId), eq(GAME.getId())))
                .willReturn(Optional.empty());

        //when
        Throwable result = catchThrowable(() -> sut.record(USER, GAME, sequenceId));

        //then
        assertThat(result).isInstanceOf(WinningPrizeNotExistException.class);
    }
}