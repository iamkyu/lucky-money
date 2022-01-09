package com.namkyujin.lucky.core.domain.token;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenMetaRepository extends JpaRepository<TokenMeta, Long> {
    Optional<TokenMeta> findByRoomId(String roomId);
}
