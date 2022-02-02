package com.namkyujin.game.application.port.out;

import com.namkyujin.game.domain.Game;
import com.namkyujin.game.domain.GameAward;

public interface AwardLockPort {

    GameAward getAndLockAward(Game game);

    void releaseAward(Game game, Long sequence);
}
