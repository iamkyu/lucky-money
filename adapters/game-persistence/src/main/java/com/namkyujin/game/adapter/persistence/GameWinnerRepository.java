package com.namkyujin.game.adapter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

interface GameWinnerRepository extends JpaRepository<GameWinnerJpaEntity, Long> {

    Optional<GameWinnerJpaEntity> findByGameIdAndUserId(Long gameId, Long userId);

    List<GameWinnerJpaEntity> findAllByGameId(Long gameId);
}
