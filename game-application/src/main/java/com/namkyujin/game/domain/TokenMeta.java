package com.namkyujin.game.domain;

import com.namkyujin.game.common.ArgumentValidator;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(indexes = {
        @Index(name = "uq_room_id", columnList = "roomId", unique = true)
})
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TokenMeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomId;

    private long idOffSet;

    @Version
    private Long version;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @Builder
    public TokenMeta(String roomId) {
        setRoomId(roomId);
        idOffSet = 0L;
        version = 0L;
    }

    /* self encapsulation */ private void setRoomId(String roomId) {
        ArgumentValidator.notEmpty(roomId, "roomId");
        this.roomId = roomId;
    }

    public void increaseOffset() {
        this.idOffSet++;
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
