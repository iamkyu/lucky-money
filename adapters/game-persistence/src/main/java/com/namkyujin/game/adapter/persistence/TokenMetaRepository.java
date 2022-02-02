package com.namkyujin.game.adapter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface TokenMetaRepository extends JpaRepository<TokenMetaJpaEntity, Long> {
    Optional<TokenMetaJpaEntity> findByRoomId(String roomId);
}
