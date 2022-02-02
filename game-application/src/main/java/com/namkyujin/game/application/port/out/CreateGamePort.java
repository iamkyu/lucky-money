package com.namkyujin.game.application.port.out;

import com.namkyujin.game.domain.Game;

public interface CreateGamePort {

    Game create(Game game);
}
