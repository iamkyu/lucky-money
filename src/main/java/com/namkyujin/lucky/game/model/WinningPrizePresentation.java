package com.namkyujin.lucky.game.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class WinningPrizePresentation {
    private final int prizeAmount;
}
