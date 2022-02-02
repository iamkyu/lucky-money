package com.namkyujin.game.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public interface GameAwardRule {

    List<Long> createAward(AwardAmount awardAmount, int totalWinners);


    class Composite implements GameAwardRule {
        private List<GameAwardRule> winningPrizeStrategies;

        public Composite(List<GameAwardRule> winningPrizeStrategies) {
            this.winningPrizeStrategies = winningPrizeStrategies;
        }

        @Override
        public List<Long> createAward(AwardAmount awardAmount, int totalWinners) {
            int randomIndex = ThreadLocalRandom.current().nextInt(winningPrizeStrategies.size());
            return winningPrizeStrategies.get(randomIndex).createAward(awardAmount, totalWinners);
        }

        /* for test */
        protected List<GameAwardRule> getPrizeStrategies() {
            return winningPrizeStrategies;
        }
    }


    class Balanced implements GameAwardRule {
        @Override
        public List<Long> createAward(AwardAmount awardAmount, int totalWinners) {
            long award = awardAmount.getValue();

            if (0 >= award || 0 >= totalWinners) {
                return Collections.emptyList();
            }

            long each = award / totalWinners;
            long remainder = award % totalWinners;

            List<Long> prizes = new ArrayList<>();
            for (int index = 0; index < totalWinners - 1; index++) {
                prizes.add(each);
            }

            prizes.add(each + remainder);
            return prizes;
        }
    }


    class AllInOne implements GameAwardRule {
        @Override
        public List<Long> createAward(AwardAmount awardAmount, int totalWinners) {
            long award = awardAmount.getValue();

            if (0 >= award || 0 >= totalWinners) {
                return Collections.emptyList();
            }

            List<Long> prizes = new ArrayList<>();

            long each = award / totalWinners;
            long remainder = award % totalWinners;

            for (int index = 0; index < totalWinners - 1; index++) {
                long minus = 0;
                if (each > 1) {
                    minus = ThreadLocalRandom.current().nextLong(1, each);
                }
                prizes.add(each - minus);
                remainder += minus;
            }

            prizes.add(each + remainder);
            return prizes;
        }
    }
}
