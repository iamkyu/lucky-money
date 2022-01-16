package com.namkyujin.game.game.application.component;

import com.namkyujin.game.core.domain.Game;
import com.namkyujin.game.core.domain.WinningPrizeRepository;
import com.namkyujin.game.game.application.rule.WinningPrizeRule;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class WinningPrizeServiceTest {
    private WinningPrizeService sut;
    private WinningPrizeRepository winningPrizeRepository;
    private TestWinningPrizeRule testWinningPrizeRule;

    @BeforeEach
    void setUp() {
        winningPrizeRepository = mock(WinningPrizeRepository.class);
        testWinningPrizeRule = new TestWinningPrizeRule();
        sut = new WinningPrizeService(winningPrizeRepository, testWinningPrizeRule);
    }

    @Test
    void createPrize_shouldCreatePrize() {
        //given
        int amount = 1000;
        int totalWinners = 3;
        Game game = Game.builder()
                .token("foo")
                .amount(amount)
                .totalWinners(totalWinners)
                .roomId("roomId")
                .createdBy(1)
                .build();


        //when
        sut.createPrize(game);

        //then
        assertThat(testWinningPrizeRule.getAmount()).isEqualTo(amount);
        assertThat(testWinningPrizeRule.getTotalWinners()).isEqualTo(totalWinners);
    }

    @Getter
    private static class TestWinningPrizeRule implements WinningPrizeRule {
        private int amount;
        private int totalWinners;

        @Override
        public List<Integer> createPrize(int amount, int totalWinners) {
            this.amount = amount;
            this.totalWinners = totalWinners;

            List<Integer> prizes = new ArrayList<>(totalWinners);
            for (int i = 0; i < totalWinners; i++) {
                prizes.add(amount);
            }
            return prizes;
        }
    }

}