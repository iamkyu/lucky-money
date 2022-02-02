package com.namkyujin.game.adapter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface GameRepository extends JpaRepository<GameJpaEntity, Long> {

    Optional<GameJpaEntity> findByTokenAndRoomId(String token, String roomId);

}
