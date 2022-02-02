package com.namkyujin.game.application.port.out;

import com.namkyujin.game.domain.GameToken;

import java.util.List;

public interface CreateTokenPort {

    void create(List<GameToken> tokens);

}
