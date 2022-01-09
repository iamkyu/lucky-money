package com.namkyujin.lucky.game.application.rule.prize;

import org.junit.jupiter.api.BeforeEach;

class BalanceWinningPrizeRuleTest extends WinningPrizeRuleTest {

    @BeforeEach
    void setUp() {
        sut = new BalanceWinningPrizeRule();
    }
}