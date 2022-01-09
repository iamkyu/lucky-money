package com.namkyujin.lucky.game.application.rule.prize;

import com.namkyujin.lucky.game.application.rule.WinningPrizeRule;
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

abstract class WinningPrizeRuleTest {
    protected WinningPrizeRule sut;

    @Test
    void shouldEmptyResultIfWinnersIsZero() {
        //when then
        List<Integer> result = sut.createPrize(1, 0);
        assertThat(result).isEmpty();
    }

    @Test
    void shouldEmptyResultIfAmountIsZero() {
        //when then
        List<Integer> result = sut.createPrize(0, 1);
        assertThat(result).isEmpty();
    }

    @TestFactory
    Stream<DynamicTest> dynamicTest() {
        //given
        List<AmountAndWinners> amountAndWinners = new ArrayList<>(Arrays.asList(
                AmountAndWinners.of(3, 3), AmountAndWinners.of(5, 3),
                AmountAndWinners.of(4, 3), AmountAndWinners.of(5, 2)
                // 당첨자가 금액보다 많은 경우는 게임 생성시 방지하고 있음.
        ));

        //when then
        return amountAndWinners.stream().map(amountAndWinner -> DynamicTest.dynamicTest(ClassUtils.getShortName(sut.getClass()), () -> {
            List<Integer> prizes = sut.createPrize(amountAndWinner.getAmount(), amountAndWinner.getTotalWinners());
            assertThat(prizes).hasSize(amountAndWinner.getTotalWinners());
            assertThat(sum(prizes)).isEqualTo(amountAndWinner.getAmount());
        }));
    }

    protected int sum(List<Integer> prizes) {
        return prizes.stream()
                .reduce(0, (a, b) -> a + b);
    }


    @Getter
    @RequiredArgsConstructor(staticName = "of")
    protected static class AmountAndWinners {
        private final int amount;
        private final int totalWinners;
    }
}