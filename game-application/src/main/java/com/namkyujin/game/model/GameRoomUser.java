package com.namkyujin.game.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
public class GameRoomUser {
    private static final String ANONYMOUS_ROOM_ID = "";
    private static final long ANONYMOUS_USER_ID = 0;

    private static final GameRoomUser ANONYMOUS = new GameRoomUser(ANONYMOUS_ROOM_ID, ANONYMOUS_USER_ID);

    private final String roomId;
    private final long userId;

    @Builder
    public GameRoomUser(String roomId, long userId) {
        this.roomId = roomId;
        this.userId = userId;
    }

    public static GameRoomUser anonymous() {
        return ANONYMOUS;
    }

    public boolean isAnonymous() {
        return (StringUtils.isEmpty(roomId) || ANONYMOUS_USER_ID >= userId)
                || this == ANONYMOUS;
    }
}
