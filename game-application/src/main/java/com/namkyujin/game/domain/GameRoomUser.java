package com.namkyujin.game.domain;

import lombok.Value;

@Value
public class GameRoomUser {
    private final GameRoomId gameRoomId;
    private final GameRoomUserId gameRoomUserId;

}
