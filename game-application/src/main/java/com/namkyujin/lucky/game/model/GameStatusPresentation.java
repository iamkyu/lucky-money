package com.namkyujin.lucky.game.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class GameStatusPresentation {
    private final LocalDateTime gameCreatedAt;
    private final int amount;
    private final int completeAmount;
    private List<Winner> winners = new ArrayList<>();

    public void addAll(Collection<Winner> winners) {
        this.winners.addAll(winners);
    }

    @Getter
    @RequiredArgsConstructor(staticName = "of")
    public static class Winner {
        private final int amount;
        private final long userId;
    }
}
