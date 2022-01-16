package com.namkyujin.game.domain;

import com.namkyujin.game.common.ArgumentValidator;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(indexes = {
        @Index(name = "uq_token_room_id", columnList = "token, roomId", unique = true),
        @Index(name = "idx_created_at_token", columnList = "createdAt, token"),
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Game {
    private static final Duration LOOK_UP_ABLE_DURATION = Duration.ofDays(7);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomId;

    private String token;

    private int amount;

    private int totalWinners;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<Winner> winners = new ArrayList<>();

    private long createdBy;

    private LocalDateTime createdAt;

    @Builder
    public Game(String roomId, String token, int amount, int totalWinners, long createdBy) {
        setRoomId(roomId);
        setToken(token);
        setAmount(amount);
        setTotalWinners(totalWinners);
        setCreatedBy(createdBy);
    }

    /* self encapsulation */ private void setRoomId(String roomId) {
        ArgumentValidator.notEmpty(roomId, "roomId");
        this.roomId = roomId;
    }

    /* self encapsulation */ private void setToken(String token) {
        ArgumentValidator.notEmpty(token, "token");
        this.token = token;
    }

    /* self encapsulation */ private void setAmount(int amount) {
        ArgumentValidator.minNumber(amount, 1, "amount");
        this.amount = amount;
    }

    /* self encapsulation */ private void setTotalWinners(int totalWinners) {
        ArgumentValidator.minNumber(totalWinners, 1, "totalWinners");
        this.totalWinners = totalWinners;
    }

    /* self encapsulation */ private void setCreatedBy(long createdBy) {
        ArgumentValidator.minNumber(createdBy, 1, "createdBy");
        this.createdBy = createdBy;
    }

    public static Duration getLookUpAbleDuration() {
        return LOOK_UP_ABLE_DURATION;
    }

    public void addWinner(Winner winner) {
        winner.setGame(this);
        winners.add(winner);
    }

    public void addWinners(Collection<Winner> winners) {
        this.winners.addAll(winners);
        winners.forEach(winner -> winner.setGame(this));
    }

    public boolean isMine(long userId) {
        return this.createdBy == userId;
    }

    @PrePersist
    protected void onPersist() {
        createdAt = LocalDateTime.now();
    }
}
