package com.namkyujin.game.adapter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface GameAwardRepository extends JpaRepository<GameAwardJpaEntity, Long> {

    Optional<GameAwardJpaEntity> findBySequenceAndGameId(long sequence, long gameId);

}
