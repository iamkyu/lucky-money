package com.namkyujin.game.application.rule.prize;

import com.namkyujin.game.application.service.BalanceWinningPrizeRule;
import org.junit.jupiter.api.BeforeEach;

class BalanceWinningPrizeRuleTest extends WinningPrizeRuleTest {

    @BeforeEach
    void setUp() {
        sut = new BalanceWinningPrizeRule();
    }
}