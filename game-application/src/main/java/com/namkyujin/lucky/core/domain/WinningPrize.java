package com.namkyujin.lucky.core.domain;


import com.namkyujin.lucky.common.ArgumentValidator;
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
public class WinningPrize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int sequence;

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn
    private Game game;

    private int prizeAmount;

    @Enumerated(EnumType.STRING)
    private WinningPrizeStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @Builder
    public WinningPrize(Game game, int sequence, int prizeAmount) {
        setGame(game);
        setSequence(sequence);
        setPrizeAmount(prizeAmount);
        status = WinningPrizeStatus.NO_WINNER;
    }

    /* self encapsulation */ private void setGame(Game game) {
        this.game = game;
    }

    /* self encapsulation */ private void setSequence(int sequence) {
        ArgumentValidator.minNumber(sequence, 1, "sequence");
        this.sequence = sequence;
    }

    /* self encapsulation */ private void setPrizeAmount(int prizeAmount) {
        ArgumentValidator.minNumber(prizeAmount, 1, "prizeAmount");
        this.prizeAmount = prizeAmount;
    }

    public void win() {
        this.status = WinningPrizeStatus.WIN;
    }

    @PrePersist
    protected void onPersist() {
        createdAt = modifiedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        modifiedAt = LocalDateTime.now();
    }
}
