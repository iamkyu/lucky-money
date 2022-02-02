package com.namkyujin.game.application.port.out;

import com.namkyujin.game.domain.GameAward;
import com.namkyujin.game.domain.GameRoomUserId;

public interface RecordGameAwardPort {

    void record(GameAward gameAward, GameRoomUserId userId);

}
