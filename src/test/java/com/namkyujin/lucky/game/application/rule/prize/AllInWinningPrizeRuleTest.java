package com.namkyujin.lucky.game.application.rule.prize;

import org.junit.jupiter.api.BeforeEach;

class AllInWinningPrizeRuleTest extends WinningPrizeRuleTest {

    @BeforeEach
    void setUp() {
        dut = new AllInWinningPrizeRule();
    }
}