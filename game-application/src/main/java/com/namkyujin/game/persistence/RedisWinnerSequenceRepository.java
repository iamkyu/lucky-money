package com.namkyujin.game.persistence;

import com.namkyujin.game.domain.WinnerSequenceRepository;
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
public class RedisWinnerSequenceRepository implements WinnerSequenceRepository {
    private static final String KEY_FORMAT = "%s:%s";
    private static final Duration TTL = Duration.ofMinutes(10L);

    private final RedisTemplate<String, Integer> redisTemplate;

    @Override
    public void saveAll(String roomId, String token, List<Integer> winnerSequences) {
        String key = toKey(roomId, token);
        winnerSequences.forEach(sequence -> redisTemplate.opsForSet().add(key, sequence));
        redisTemplate.expire(key, TTL.toMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public Optional<Integer> getOne(String roomId, String token) {
        Integer sequence = redisTemplate.opsForSet().pop(toKey(roomId, token));
        return Optional.ofNullable(sequence);
    }

    @Override
    public void release(String roomId, String token, Integer sequence) {
        redisTemplate.opsForSet().add(toKey(roomId, token), sequence);
    }

    private String toKey(String roomId, String token) {
        return String.format(KEY_FORMAT, roomId, token) ;
    }
}
