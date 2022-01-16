package com.namkyujin.game.game.application.rule.prize;

import org.junit.jupiter.api.BeforeEach;

class AllInWinningPrizeRuleTest extends WinningPrizeRuleTest {

    @BeforeEach
    void setUp() {
        sut = new AllInWinningPrizeRule();
    }
}