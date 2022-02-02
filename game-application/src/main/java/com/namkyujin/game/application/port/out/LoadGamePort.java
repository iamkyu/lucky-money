package com.namkyujin.game.application.port.out;

import com.namkyujin.game.domain.Game;
import com.namkyujin.game.domain.GameRoomId;
import com.namkyujin.game.domain.GameToken;

public interface LoadGamePort {

    Game load(GameToken token, GameRoomId gameRoomId);

}
