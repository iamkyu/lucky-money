package com.namkyujin.game.application.service.exception;

public class GameClosedException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "다음 기회에";

    public GameClosedException() {
        super(DEFAULT_MESSAGE);
    }
}
