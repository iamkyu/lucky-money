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
@Table(indexes = {
        @Index(name = "uq_token_room_id", columnList = "token, roomId", unique = true),
        @Index(name = "idx_created_at_token", columnList = "createdAt, token"),
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class GameJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomId;

    private String token;

    private Long amount;

    private Integer totalWinners;

    private Long createdBy;

    private LocalDateTime createdAt;

    public GameJpaEntity(String roomId, String token, Long amount, Integer totalWinners, Long createdBy) {
        this.roomId = roomId;
        this.token = token;
        this.amount = amount;
        this.totalWinners = totalWinners;
        this.createdBy = createdBy;
    }

    @PrePersist
    protected void onPersist() {
        createdAt = LocalDateTime.now();
    }
}
