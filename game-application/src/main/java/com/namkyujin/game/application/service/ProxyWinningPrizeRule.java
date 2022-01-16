package com.namkyujin.game.application.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Primary
@Component
public class ProxyWinningPrizeRule implements WinningPrizeRule {
    private List<WinningPrizeRule> winningPrizeStrategies;

    public ProxyWinningPrizeRule(List<WinningPrizeRule> winningPrizeStrategies) {
        this.winningPrizeStrategies = winningPrizeStrategies;
    }

    @Override
    public List<Integer> createPrize(int amount, int totalWinners) {
        int randomIndex = ThreadLocalRandom.current().nextInt(winningPrizeStrategies.size());
        return winningPrizeStrategies.get(randomIndex).createPrize(amount, totalWinners);
    }

    /* for test */ protected List<WinningPrizeRule> getPrizeStrategies() {
        return winningPrizeStrategies;
    }
}
