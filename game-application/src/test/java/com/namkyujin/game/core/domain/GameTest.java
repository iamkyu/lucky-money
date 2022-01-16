package com.namkyujin.game.core.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GameTest {

    @Test
    void isMine_shouldTrueIfCreatedByMine() {
        //given
        int createdBy = 1;
        Game sut = Game.builder()
                .token("foo")
                .amount(1)
                .totalWinners(1)
                .roomId("roomId")
                .createdBy(createdBy)
                .build();

        //when then
        assertThat(sut.isMine(createdBy)).isTrue();
    }

    @Test
    void isMine_shouldFalseIfCreatedByOthers() {
        //given
        int createdBy = 1;
        Game sut = Game.builder()
                .token("foo")
                .amount(1)
                .totalWinners(1)
                .roomId("roomId")
                .createdBy(createdBy + 100)
                .build();

        //when then
        assertThat(sut.isMine(createdBy)).isFalse();
    }
}