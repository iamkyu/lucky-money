package com.namkyujin.game.core.domain;

import com.namkyujin.game.common.ArgumentValidator;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(name = "uq_game_id_user_id", columnList = "game_id, userId", unique = true)
})
public class Winner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn
    private Game game;

    private long userId;

    private int prizeAmount;

    private LocalDateTime createdAt;

    @Builder
    public Winner(Game game, long userId, int prizeAmount) {
        setGame(game);
        setUserId(userId);
        setPrizeAmount(prizeAmount);
    }

    /* self encapsulation */ private void setUserId(long userId) {
        ArgumentValidator.minNumber(userId, 1, "userId");
        this.userId = userId;
    }

    /* self encapsulation */ private void setPrizeAmount(int prizeAmount) {
        ArgumentValidator.minNumber(userId, 1, "prizeAmount");
        this.prizeAmount = prizeAmount;
    }


    public void setGame(Game game) {
        this.game = game;
    }

    @PrePersist
    protected void onPersist() {
        createdAt = LocalDateTime.now();
    }
}
