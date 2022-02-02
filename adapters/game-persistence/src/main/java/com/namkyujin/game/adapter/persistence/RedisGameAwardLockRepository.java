package com.namkyujin.game.adapter.persistence;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Repository
@RequiredArgsConstructor
class RedisGameAwardLockRepository implements GameAwardLockRepository {
    private static final String KEY_FORMAT = "%s:%s";
    private static final Duration TTL = Duration.ofMinutes(10L);

    private final RedisTemplate<String, Long> gameAwardRedisTemplate;

    @Override
    public void saveAll(String roomId, String token, List<Long> winnerSequences) {
        String key = toKey(roomId, token);
        winnerSequences.forEach(sequence -> gameAwardRedisTemplate.opsForSet().add(key, sequence));
        gameAwardRedisTemplate.expire(key, TTL.toMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public Optional<Long> lock(String roomId, String token) {
        Long sequence = gameAwardRedisTemplate.opsForSet().pop(toKey(roomId, token));
        return Optional.ofNullable(sequence);
    }

    @Override
    public void release(String roomId, String token, Long sequence) {
        gameAwardRedisTemplate.opsForSet().add(toKey(roomId, token), sequence);
    }

    private String toKey(String roomId, String token) {
        return String.format(KEY_FORMAT, roomId, token) ;
    }
}
