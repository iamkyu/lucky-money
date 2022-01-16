package com.namkyujin.game.game.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class CreatedGamePresentation {
    private final String token;
}
