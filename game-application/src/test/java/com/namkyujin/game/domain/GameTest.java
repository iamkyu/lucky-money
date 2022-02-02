package com.namkyujin.game.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GameTest {

    @Test
    void isMine_shouldTrueIfCreatedByMine() {
        //given
        long createdBy = 1L;
        Game sut = createGame(createdBy);

        //when then
        assertThat(sut.isMine(new GameRoomUserId(createdBy))).isTrue();
    }

    @Test
    void isMine_shouldFalseIfCreatedByOthers() {
        //given
        long createdBy = 1L;
        Game sut = createGame(createdBy + 1000L);

        //when then
        assertThat(sut.isMine(new GameRoomUserId(createdBy))).isFalse();
    }

    private Game createGame(long createdBy) {
        return Game.withoutId(
                new GameRoomId("rooId"),
                new GameToken("foo"),
                new AwardAmount(1L),
                1,
                new GameRoomUserId(createdBy)
        );
    }
}