package com.namkyujin.lucky.game.application.rule;

import java.util.List;

public interface WinningPrizeRule {
    List<Integer> createPrize(int amount, int totalWinners);
}
