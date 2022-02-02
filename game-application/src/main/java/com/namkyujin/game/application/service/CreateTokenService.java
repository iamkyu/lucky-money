package com.namkyujin.game.application.service;

import com.namkyujin.common.UseCase;
import com.namkyujin.game.application.port.out.LoadTokenPort;
import com.namkyujin.game.application.service.exception.TokenCreationFailedException;
import com.namkyujin.game.domain.GameRoomId;
import com.namkyujin.game.domain.GameToken;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;

@UseCase
@RequiredArgsConstructor
class CreateTokenService {

    private final LoadTokenPort loadTokenPort;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public GameToken create(GameRoomId gameRoomId) {
        try {
            return loadTokenPort.loadToken(gameRoomId);
        } catch (Exception e) {
            throw new TokenCreationFailedException();
        }
    }
}
