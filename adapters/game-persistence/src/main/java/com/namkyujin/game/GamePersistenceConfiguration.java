package com.namkyujin.game;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Configuration
public class GamePersistenceConfiguration {

    @Bean
    public RedisTemplate<String, Long> gameAwardRedisTemplate(LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Long> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericToStringSerializer<>(Long.class));
        return redisTemplate;
    }

    @Bean
    @Profile({"test", "local"})
    public EmbeddedRedisConfig embeddedRedisConfig() {
        return new EmbeddedRedisConfig();
    }


    public class EmbeddedRedisConfig {

        @Value("${spring.redis.port}")
        private int redisPort;

        private RedisServer redisServer;

        @PostConstruct
        public void redisServer() throws IOException {
            if (isRedisRunning()) return;

            redisServer = new RedisServer(redisPort);
            redisServer.start();
        }

        @PreDestroy
        public void stopRedis() {
            if (redisServer != null) {
                redisServer.stop();
            }
        }

        /**
         * Embedded Redis가 현재 실행중인지 확인
         */
        private boolean isRedisRunning() throws IOException {
            return isRunning(executeGrepProcessCommand(redisPort));
        }

        /**
         * 해당 port를 사용중인 프로세스 확인하는 sh 실행
         */
        private Process executeGrepProcessCommand(int port) throws IOException {
            String command = String.format("netstat -nat | grep LISTEN|grep %d", port);
            String[] shell = {"/bin/sh", "-c", command};
            return Runtime.getRuntime().exec(shell);
        }

        /**
         * 해당 Process가 현재 실행중인지 확인
         */
        private boolean isRunning(Process process) {
            String line;
            StringBuilder pidInfo = new StringBuilder();

            try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                while ((line = input.readLine()) != null) {
                    pidInfo.append(line);
                }
            } catch (Exception e) {
            }
            return !StringUtils.isEmpty(pidInfo.toString());
        }
    }
}
