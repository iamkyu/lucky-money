package com.namkyujin.game.application.port.out;

import com.namkyujin.game.domain.GameToken;
import com.namkyujin.game.domain.GameRoomId;

public interface LoadTokenPort {

    GameToken loadToken(GameRoomId gameRoomId);

}
