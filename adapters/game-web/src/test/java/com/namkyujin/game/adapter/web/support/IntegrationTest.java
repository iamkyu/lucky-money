package com.namkyujin.game.adapter.web.support;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@Tag("integration")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class IntegrationTest {
    @Autowired
    protected TestRestTemplate localServerRestTemplate;

    protected final <T> ResponseEntity get(String url, Class<T> responseType) {
        return localServerRestTemplate.getForEntity(url, null, responseType);
    }

    protected final <T> ResponseEntity post(String url, @Nullable Object request, Class<T> responseType) {
        return localServerRestTemplate.postForEntity(url, request, responseType);
    }

    protected final <T> ResponseEntity put(String url, @Nullable HttpEntity request, Class<T> responseType) {
        return localServerRestTemplate.exchange(url, HttpMethod.PUT, request, responseType);
    }
}
