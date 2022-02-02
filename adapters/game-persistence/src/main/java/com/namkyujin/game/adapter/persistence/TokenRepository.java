package com.namkyujin.game.adapter.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface TokenRepository extends JpaRepository<TokenJpaEntity, Long> {
}
