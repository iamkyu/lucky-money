package com.namkyujin.game.application.service.exception;

import com.namkyujin.game.domain.Game;

public class GameNotExistException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "게임이 종료되었거나 존재하지 않습니다.";
    private static final String MESSAGE_FORMAT = "게임이 종료되었거나 존재하지 않습니다. (%s)";

    public GameNotExistException() {
        super(DEFAULT_MESSAGE);
    }

    public GameNotExistException(Game.GameId id) {
        super(String.format(MESSAGE_FORMAT, id));
    }
}
