package com.namkyujin.game.domain;

import org.junit.jupiter.api.BeforeEach;

class AllInOneTest extends GameAwardRuleTest {

    @BeforeEach
    void setUp() {
        sut = new GameAwardRule.AllInOne();
    }
}