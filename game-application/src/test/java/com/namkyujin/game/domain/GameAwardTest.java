package com.namkyujin.game.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GameAwardTest {
    @Test
    void win_shouldChangeStatusToWin() {
        //given
        GameAward sut = GameAward
                .withoutId(1L, null, new AwardAmount(1000L));

        assertThat(sut.getStatus()).isEqualTo(GameAward.Status.NO_WINNER);

        //when
        GameAward result = sut.win();

        //then
        assertThat(result.getStatus()).isEqualTo(GameAward.Status.WIN);
    }
}