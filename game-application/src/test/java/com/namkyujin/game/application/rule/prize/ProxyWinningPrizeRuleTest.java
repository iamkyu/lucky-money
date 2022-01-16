package com.namkyujin.game.application.rule.prize;

import com.namkyujin.game.application.service.ProxyWinningPrizeRule;
import com.namkyujin.game.support.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class ProxyWinningPrizeRuleTest extends IntegrationTest {

    @Autowired
    private ProxyWinningPrizeRule proxyWinningPrizeRule;

    @Test
    void shouldRulesAreNotEmpty() {
        assertThat(proxyWinningPrizeRule.getPrizeStrategies()).hasSizeGreaterThan(1);
    }
}