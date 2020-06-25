package com.namkyujin.lucky.core.domain;

import java.util.List;
import java.util.Optional;

public interface WinnerSequenceRepository {
    void saveAll(String roomId, String token, List<Integer> winnerSequences);

    Optional<Integer> getOne(String roomId, String token);

    void release(String roomId, String token, Integer sequence);
}
