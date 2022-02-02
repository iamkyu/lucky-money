package com.namkyujin.game.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

abstract class GameAwardRuleTest {
    protected GameAwardRule sut;

    @Test
    void shouldEmptyResultIfWinnersIsZero() {
        //when then
        List<Long> result = sut.createAward(new AwardAmount(1L), 0);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldEmptyResultIfAmountIsZero() {
        //when then
        List<Long> result = sut.createAward(new AwardAmount(1L), 0);
        assertThat(result).isEmpty();
    }

    @TestFactory
    Stream<DynamicTest> dynamicTest() {
        //given
        List<AmountAndWinners> amountAndWinners = new ArrayList<>(Arrays.asList(
                AmountAndWinners.of(3L, 3), AmountAndWinners.of(5L, 3),
                AmountAndWinners.of(4L, 3), AmountAndWinners.of(5L, 2)
                // 당첨자가 금액보다 많은 경우는 게임 생성시 방지하고 있음.
        ));

        //when then
        return amountAndWinners.stream().map(amountAndWinner -> DynamicTest.dynamicTest(ClassUtils.getShortName(sut.getClass()), () -> {
            List<Long> prizes = sut.createAward(new AwardAmount(amountAndWinner.getAmount()), amountAndWinner.getTotalWinners());
            assertThat(prizes).hasSize(amountAndWinner.getTotalWinners());
            assertThat(sum(prizes)).isEqualTo(amountAndWinner.getAmount());
        }));
    }

    protected Long sum(List<Long> prizes) {
        return prizes.stream()
                .reduce(0L, Long::sum);
    }


    @Getter
    @RequiredArgsConstructor(staticName = "of")
    protected static class AmountAndWinners {
        private final long amount;
        private final int totalWinners;
    }
}