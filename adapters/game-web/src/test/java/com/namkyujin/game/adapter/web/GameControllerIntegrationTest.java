package com.namkyujin.game.adapter.web;

import com.namkyujin.game.adapter.web.GameController.CreateGameRequest;
import com.namkyujin.game.adapter.web.support.IntegrationTest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class GameControllerIntegrationTest extends IntegrationTest {
    private static final String USER_ID_HEADER_KEY_NAME = "X-USER-ID";
    private static final String ROOM_ID_HEADER_KEY_NAME = "X-ROOM-ID";


    @ParameterizedTest
    @MethodSource("provideWrongHeaders")
    @DisplayName("잘못된 헤더 - 401")
    void shouldUnauthorized(@Nullable HttpHeaders headers) {
        //given
        CreateGameRequest request = new CreateGameRequest(1L, 1);
        HttpEntity<CreateGameRequest> entity = new HttpEntity<>(request, headers);

        //when
        ResponseEntity<String> response = post(GameController.BASE_PATH, entity, String.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    private static Stream<Arguments> provideWrongHeaders() {
        return Stream.of(
                Arguments.of(generateHeaders(Tuple.of(USER_ID_HEADER_KEY_NAME, "1"))),
                // romId only
                Arguments.of(generateHeaders(Tuple.of(ROOM_ID_HEADER_KEY_NAME, "1"))),
                // illegal user id
                Arguments.of(generateHeaders(Tuple.of(ROOM_ID_HEADER_KEY_NAME, "1"), Tuple.of(USER_ID_HEADER_KEY_NAME, "number")))
        );
    }


    @ParameterizedTest
    @MethodSource("provideWrongRequest")
    @DisplayName("잘못된 요청 - 400")
    void shouldBadRequest1(CreateGameRequest createGameRequest) {
        //given
        HttpHeaders headers = generateHeaders(Tuple.of(USER_ID_HEADER_KEY_NAME, "1"), Tuple.of(ROOM_ID_HEADER_KEY_NAME, "lucky"));
        HttpEntity<CreateGameRequest> entity = new HttpEntity<>(createGameRequest, headers);

        //when
        ResponseEntity<String> response = post(GameController.BASE_PATH, entity, String.class);

        //then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }


    private static Stream<Arguments> provideWrongRequest() {
        return Stream.of(
                // 잘못된 금액, 당첨자
                Arguments.of(new CreateGameRequest(0L, 0)),
                Arguments.of(new CreateGameRequest(-99L, -99)),
                // 잘못된 당첨자
                Arguments.of(new CreateGameRequest(1L, 0)),
                Arguments.of(new CreateGameRequest(1L, -83)),
                //잘못된 금액
                Arguments.of(new CreateGameRequest(0L, 3)),
                Arguments.of(new CreateGameRequest(-102L, 3))
        );
    }


    private static HttpHeaders generateHeaders(Tuple... tuples) {
        HttpHeaders httpHeaders = new HttpHeaders();
        Arrays.stream(tuples).forEach(tuple -> httpHeaders.add(tuple.getT1(), tuple.getT2()) );
        return httpHeaders;
    }


    @Getter
    @RequiredArgsConstructor(staticName = "of")
    private static class Tuple {
        private final String t1;
        private final String t2;
    }
}