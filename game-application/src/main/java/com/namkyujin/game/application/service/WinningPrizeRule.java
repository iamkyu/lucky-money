package com.namkyujin.game.application.service;

import java.util.List;

public interface WinningPrizeRule {
    List<Integer> createPrize(int amount, int totalWinners);


}
