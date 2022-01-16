package com.namkyujin.game.core.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByTokenAndRoomId(String token, String roomId);
    Optional<Game> findByTokenAndCreatedAtAfter(String token, LocalDateTime createdAt);
}
