package com.namkyujin.game.game.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class CreateGameCommand {
    private final int amount;
    private final int totalWinners;
}
