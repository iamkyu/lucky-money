package com.namkyujin.game.game.application.rule;

import java.util.List;

public interface WinningPrizeRule {
    List<Integer> createPrize(int amount, int totalWinners);
}
