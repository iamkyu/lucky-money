package com.namkyujin.game.application.port.out;

import com.namkyujin.game.domain.Game;
import com.namkyujin.game.domain.GameWinner;

import java.util.List;

public interface LoadGameWinnerPort {

    List<GameWinner> load(Game.GameId gameId);

}
