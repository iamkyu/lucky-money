package com.namkyujin.game.support;

import com.namkyujin.game.domain.WinnerSequenceRepository;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class TestInMemoryWinnerSequenceRepository implements WinnerSequenceRepository {
    private static final String KEY_FORMAT = "%s:%s";
    private Map<String, List<Integer>> keyToSequences = new HashMap<>();


    @Override
    public void saveAll(String roomId, String token, List<Integer> winnerSequences) {
        String key = toKey(roomId, token);
        winnerSequences.forEach(sequence -> keyToSequences.put(key, winnerSequences));
    }

    @Override
    public Optional<Integer> getOne(String roomId, String token) {
        List<Integer> sequences = keyToSequences.getOrDefault(toKey(roomId, token), new ArrayList<>());
        if (CollectionUtils.isEmpty(sequences)) {
            return Optional.empty();
        }

        Integer sequence = sequences.remove(0);
        keyToSequences.put(toKey(roomId, token), sequences);

        return Optional.ofNullable(sequence);
    }

    @Override
    public void release(String roomId, String token, Integer sequence) {
        List<Integer> sequences = new ArrayList<>();
        sequences.add(sequence);
        keyToSequences.put(toKey(roomId, token), sequences);
    }

    /* for test*/ public boolean isExsistSequence(String roomId, String token, Integer sequence) {
        List<Integer> sequences = keyToSequences.get(toKey(roomId, token));
        return sequences.contains(sequence);
    }


    private String toKey(String roomId, String token) {
        return String.format(KEY_FORMAT, roomId, token) ;
    }

}
