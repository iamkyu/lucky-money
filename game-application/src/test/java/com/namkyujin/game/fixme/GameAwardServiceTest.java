package com.namkyujin.game.fixme;

/**
 * FIXME 아키텍처 변경과정에서 수정하지 못한 테스트
 */
class GameAwardServiceTest {
    // private WinningPrizeService sut;
    // private WinningPrizeRepository winningPrizeRepository;
    // private TestGameAwardRule testWinningPrizeRule;
    //
    // @BeforeEach
    // void setUp() {
    //     winningPrizeRepository = mock(WinningPrizeRepository.class);
    //     testWinningPrizeRule = new TestGameAwardRule();
    //     sut = new WinningPrizeService(winningPrizeRepository, testWinningPrizeRule);
    // }
    //
    // @Test
    // void createPrize_shouldCreatePrize() {
    //     //given
    //     int amount = 1000;
    //     int totalWinners = 3;
    //     Game game = Game.builder()
    //             .token("foo")
    //             .amount(amount)
    //             .totalWinners(totalWinners)
    //             .roomId("roomId")
    //             .createdBy(1)
    //             .build();
    //
    //
    //     //when
    //     sut.createPrize(game);
    //
    //     //then
    //     assertThat(testWinningPrizeRule.getAmount()).isEqualTo(amount);
    //     assertThat(testWinningPrizeRule.getTotalWinners()).isEqualTo(totalWinners);
    // }
    //
    // @Getter
    // private static class TestGameAwardRule implements GameAwardRule {
    //     private int amount;
    //     private int totalWinners;
    //
    //     @Override
    //     public List<Long> createAward(AwardAmount awardAmount, int totalWinners) {
    //         this.amount = awardAmount;
    //         this.totalWinners = totalWinners;
    //
    //         List<Integer> prizes = new ArrayList<>(totalWinners);
    //         for (int i = 0; i < totalWinners; i++) {
    //             prizes.add(awardAmount);
    //         }
    //         return prizes;
    //     }
    // }

}