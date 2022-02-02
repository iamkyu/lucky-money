package com.namkyujin.game.adapter.persistence;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
        @Index(name = "uq_game_id_user_id", columnList = "gameId, userId", unique = true)
})
class GameWinnerJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long gameId;

    private Long userId;

    private Long prizeAmount;

    private LocalDateTime createdAt;

    public GameWinnerJpaEntity(Long gameId, Long userId, Long prizeAmount) {
        this.gameId = gameId;
        this.userId = userId;
        this.prizeAmount = prizeAmount;
    }

    @PrePersist
    protected void onPersist() {
        createdAt = LocalDateTime.now();
    }
}
