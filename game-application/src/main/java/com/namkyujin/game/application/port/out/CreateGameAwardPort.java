package com.namkyujin.game.application.port.out;

import com.namkyujin.game.domain.Game;
import com.namkyujin.game.domain.GameAward;

import java.util.List;

public interface CreateGameAwardPort {

    void create(Game game, List<GameAward> gameAwards);

}
