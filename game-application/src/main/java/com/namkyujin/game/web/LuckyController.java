package com.namkyujin.game.web;

import com.namkyujin.game.application.service.LuckyService;
import com.namkyujin.game.common.exception.UnauthorizedGameRoomUserException;
import com.namkyujin.game.common.model.CommonResponse;
import com.namkyujin.game.model.CreateGameCommand;
import com.namkyujin.game.model.CreatedGamePresentation;
import com.namkyujin.game.model.GameRoomUser;
import com.namkyujin.game.model.GameStatusPresentation;
import com.namkyujin.game.model.GameToken;
import com.namkyujin.game.model.WinningPrizePresentation;
import com.namkyujin.game.web.model.CreateGameRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(LuckyController.BASE_PATH)
@RestController
@RequiredArgsConstructor
public class LuckyController {
    protected static final String BASE_PATH = "/v1/lucky";

    private final LuckyService luckyService;

    @PostMapping
    public CommonResponse<CreatedGamePresentation> crateGame(GameRoomUser gameRoomUser,
                                                             @RequestBody CreateGameRequest request,
                                                             BindingResult bindingResult) {
        validateUser(gameRoomUser);
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        if (request.winnersGreaterThenAmount()) {
            throw new IllegalArgumentException("인원수보다 많은 금액을 입력하세요.");
        }

        CreateGameCommand command = CreateGameCommand.of(request.getAmount(), request.getTotalWinners());
        CreatedGamePresentation presentation = luckyService.createGame(gameRoomUser, command);

        return CommonResponse.of(presentation);
    }


    @PutMapping
    public CommonResponse<WinningPrizePresentation> getWinningPrize(GameRoomUser gameRoomUser,
                                                                    GameToken gameToken) {
        validateUser(gameRoomUser);
        validateToken(gameToken);

        return CommonResponse.of(luckyService.getPrize(gameRoomUser, gameToken));
    }


    @GetMapping
    public CommonResponse<GameStatusPresentation> getStatus(GameRoomUser gameRoomUser,
                                                            GameToken gameToken) {
        validateUser(gameRoomUser);
        validateToken(gameToken);

        return CommonResponse.of(luckyService.getGameStatus(gameRoomUser, gameToken));
    }

    private void validateUser(GameRoomUser gameRoomUser) {
        if (gameRoomUser.isAnonymous()) throw new UnauthorizedGameRoomUserException();
    }

    private void validateToken(GameToken gameToken) {
        if (gameToken.invalid()) throw new IllegalArgumentException("게임 정보가 올바르지 않습니다.");
    }
}
