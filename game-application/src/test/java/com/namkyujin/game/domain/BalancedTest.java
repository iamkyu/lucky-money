package com.namkyujin.game.domain;

import org.junit.jupiter.api.BeforeEach;

class BalancedTest extends GameAwardRuleTest {

    @BeforeEach
    void setUp() {
        sut = new GameAwardRule.Balanced();
    }
}