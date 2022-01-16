package com.namkyujin.game.domain;

public enum WinningPrizeStatus {
    NO_WINNER("당첨자 없음"),
    WIN("당첨자 있음");

    private String description;

    WinningPrizeStatus(String description) {
        this.description = description;
    }
}
