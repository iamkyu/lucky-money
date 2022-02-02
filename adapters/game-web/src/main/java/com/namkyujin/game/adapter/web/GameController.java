package com.namkyujin.game.adapter.web;

import com.namkyujin.common.WebAdapter;
import com.namkyujin.game.application.port.in.CreateGameUseCase;
import com.namkyujin.game.application.port.in.GetGameStatusQuery;
import com.namkyujin.game.application.port.in.JoinGameUseCase;
import com.namkyujin.game.domain.GameRoomUser;
import com.namkyujin.game.domain.GameToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequestMapping(GameController.BASE_PATH)
@RequiredArgsConstructor
class GameController {
    protected static final String BASE_PATH = "/v1/lucky";

    private final CreateGameUseCase createGameUseCase;
    private final JoinGameUseCase joinGameUseCase;
    private final GetGameStatusQuery getGameStatusQuery;

    @PostMapping
    CommonResponse<CreateGameUseCase.CreatedGamePresentation> create(
            GameRoomUser gameRoomUser,
            @RequestBody CreateGameRequest request) {

        CreateGameUseCase.CreateGameCommand command = new CreateGameUseCase.CreateGameCommand(request.getAmount(), request.getTotalWinners());

        return CommonResponse.of(createGameUseCase.createGame(gameRoomUser, command));
    }

    @PutMapping
    CommonResponse<JoinGameUseCase.WinningPrizePresentation> join(GameRoomUser gameRoomUser, GameToken gameToken) {
        return CommonResponse.of(joinGameUseCase.join(gameRoomUser, gameToken));
    }

    @GetMapping
    CommonResponse<GetGameStatusQuery.GameStatusPresentation> getStatus(GameRoomUser gameRoomUser, GameToken gameToken) {
        return CommonResponse.of(getGameStatusQuery.getStatus(gameRoomUser, gameToken));
    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    static class CreateGameRequest {
        private Long amount;
        private Integer totalWinners;
    }
}
