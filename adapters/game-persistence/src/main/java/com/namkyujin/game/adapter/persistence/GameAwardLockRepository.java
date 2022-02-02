package com.namkyujin.game.adapter.persistence;

import java.util.List;
import java.util.Optional;

interface GameAwardLockRepository {
    void saveAll(String roomId, String token, List<Long> winnerSequences);

    Optional<Long> lock(String roomId, String token);

    void release(String roomId, String token, Long sequence);
}
