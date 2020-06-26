package com.namkyujin.lucky.game.application.component;

import com.namkyujin.lucky.core.domain.Game;
import com.namkyujin.lucky.core.domain.GameRepository;
import com.namkyujin.lucky.game.model.CreateGameCommand;
import com.namkyujin.lucky.game.model.GameRoomUser;
import com.namkyujin.lucky.game.model.GameToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;

    @Transactional
    public Game createGame(String token, GameRoomUser gameRoomUser, CreateGameCommand command) {
        Game game = gameRepository.save(map(gameRoomUser, token, command));
        return game;
    }

    public Optional<Game> getGame(GameToken token, String roomId) {
        Optional<Game> maybeGame = gameRepository.findByTokenAndRoomId(token.get(), roomId);
        return maybeGame;
    }

    public Optional<Game> getLookUpAbleGame(GameToken token, LocalDateTime baseDateTime) {
        Optional<Game> maybeGame = gameRepository.findByTokenAndCreatedAtAfter(token.get(), baseDateTime);
        return maybeGame;
    }


    private Game map(GameRoomUser gameRoomUser, String token, CreateGameCommand command) {
        return Game.builder()
                .roomId(gameRoomUser.getRoomId())
                .token(token)
                .amount(command.getAmount())
                .totalWinners(command.getTotalWinners())
                .createdBy(gameRoomUser.getUserId())
                .build();
    }
}
