package com.namkyujin.game.adapter.persistence;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TestGameAwardLockRepository implements GameAwardLockRepository {
    private static final String KEY_FORMAT = "%s:%s";
    private Map<String, List<Long>> keyToSequences = new HashMap<>();


    @Override
    public void saveAll(String roomId, String token, List<Long> winnerSequences) {
        String key = toKey(roomId, token);
        winnerSequences.forEach(sequence -> keyToSequences.put(key, winnerSequences));
    }

    @Override
    public Optional<Long> lock(String roomId, String token) {
        List<Long> sequences = keyToSequences.getOrDefault(toKey(roomId, token), new ArrayList<>());
        if (CollectionUtils.isEmpty(sequences)) {
            return Optional.empty();
        }

        Long sequence = sequences.remove(0);
        keyToSequences.put(toKey(roomId, token), sequences);

        return Optional.ofNullable(sequence);
    }

    @Override
    public void release(String roomId, String token, Long sequence) {
        List<Long> sequences = new ArrayList<>();
        sequences.add(sequence);
        keyToSequences.put(toKey(roomId, token), sequences);
    }

    /* for test*/ public boolean isExsistSequence(String roomId, String token, Long sequence) {
        List<Long> sequences = keyToSequences.get(toKey(roomId, token));
        return sequences.contains(sequence);
    }


    private String toKey(String roomId, String token) {
        return String.format(KEY_FORMAT, roomId, token) ;
    }

}
