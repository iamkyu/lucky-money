package com.namkyujin.game.core.domain;

import com.namkyujin.game.domain.WinningPrize;
import com.namkyujin.game.domain.WinningPrizeStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WinningPrizeTest {
    @Test
    void win_shouldChangeStatusToWin() {
        //given
        WinningPrize sut = WinningPrize.builder()
                .game(null)
                .sequence(1)
                .prizeAmount(1000)
                .build();

        assertThat(sut.getStatus()).isEqualTo(WinningPrizeStatus.NO_WINNER);

        //when
        sut.win();

        //then
        assertThat(sut.getStatus()).isEqualTo(WinningPrizeStatus.WIN);
    }
}