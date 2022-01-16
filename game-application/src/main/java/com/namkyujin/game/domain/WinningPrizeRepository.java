package com.namkyujin.game.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WinningPrizeRepository extends JpaRepository<WinningPrize, Long> {
    Optional<WinningPrize> findBySequenceAndGameId(int sequence, long gameId);
}
