package com.namkyujin.game.application.rule.prize;

import com.namkyujin.game.application.service.AllInWinningPrizeRule;
import org.junit.jupiter.api.BeforeEach;

class AllInWinningPrizeRuleTest extends WinningPrizeRuleTest {

    @BeforeEach
    void setUp() {
        sut = new AllInWinningPrizeRule();
    }
}