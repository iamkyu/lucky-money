package com.namkyujin.game.adapter.persistence;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class GameAwardJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sequence;

    private Long gameId;

    private Long prizeAmount;

    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public GameAwardJpaEntity(Long gameId, Long sequence, Long prizeAmount, String status) {
        this.gameId = gameId;
        this.sequence = sequence;
        this.prizeAmount = prizeAmount;
        this.status = status;
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
