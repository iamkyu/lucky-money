package com.namkyujin.game.application.service.exception;

public class UnauthorizedGameRoomUserException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "인증되지 않은 요청입니다.";

    public UnauthorizedGameRoomUserException() {
        super(DEFAULT_MESSAGE);
    }
}
