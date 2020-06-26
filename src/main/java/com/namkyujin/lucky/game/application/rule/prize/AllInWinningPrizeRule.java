package com.namkyujin.lucky.game.application.rule.prize;

import com.namkyujin.lucky.game.application.rule.WinningPrizeRule;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class AllInWinningPrizeRule implements WinningPrizeRule {
    
    @Override
    public List<Integer> createPrize(int amount, int totalWinners) {
        if (0 >= amount || 0 >= totalWinners) {
            return Collections.emptyList();
        }

        List<Integer> prizes = new ArrayList<>();

        int each = amount / totalWinners;
        int remainder = amount % totalWinners;

        for (int index = 0; index < totalWinners -1; index++) {
            int minus = 0;
            if (each > 1) {
                minus = ThreadLocalRandom.current().nextInt(1, each);
            }
            prizes.add(each - minus);
            remainder += minus;
        }

        prizes.add(each + remainder);
        return prizes;
    }
}
