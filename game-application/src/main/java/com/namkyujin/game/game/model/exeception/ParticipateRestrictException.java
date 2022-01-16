package com.namkyujin.game.game.model.exeception;

public class ParticipateRestrictException extends RuntimeException {
    private static final String RESTRICT_OWN_MESSAGE = "자기 자신은 참여 불가능 합니다.";
    private static final String RESTRICT_ALREADY_MESSAGE = "이미 게임에 참여 했습니다.";
    private static final String RESTRICT_MESSAGE = "게임에 참여 불가능 합니다.";

    public ParticipateRestrictException() {
        super(RESTRICT_MESSAGE);
    }

    public ParticipateRestrictException(String message) {
        super(message);
    }

    public static ParticipateRestrictException own() {
        return new ParticipateRestrictException(RESTRICT_OWN_MESSAGE);
    }

    public static ParticipateRestrictException already() {
        return new ParticipateRestrictException(RESTRICT_ALREADY_MESSAGE);
    }
}
