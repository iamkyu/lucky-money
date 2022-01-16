package com.namkyujin.game.game.application.rule.prize;

import com.namkyujin.game.game.application.rule.WinningPrizeRule;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class BalanceWinningPrizeRule implements WinningPrizeRule {

    @Override
    public List<Integer> createPrize(int amount, int totalWinners) {
        if (0 >= amount || 0 >= totalWinners) {
            return Collections.emptyList();
        }

        int each = amount / totalWinners;
        int remainder = amount % totalWinners;

        List<Integer> prizes = new ArrayList<>();
        for (int index = 0; index < totalWinners -1; index++) {
            prizes.add(each);
        }

        prizes.add(each + remainder);
        return prizes;
    }
}
