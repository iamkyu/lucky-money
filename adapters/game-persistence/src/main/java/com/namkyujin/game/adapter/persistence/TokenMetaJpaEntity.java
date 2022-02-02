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
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Version;
import java.time.LocalDateTime;

@Entity
@Table(indexes = {
        @Index(name = "uq_room_id", columnList = "roomId", unique = true)
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class TokenMetaJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomId;

    private Long idOffSet;

    @Version
    private Long version;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public TokenMetaJpaEntity(String roomId) {
        this.roomId = roomId;
        idOffSet = 0L;
        version = 0L;
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
