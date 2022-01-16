package com.namkyujin.game.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ArgumentValidator {

    /**
     * Null 아님을 확인
     *
     * @param target       확인 대상
     * @param argumentName 인자명
     * @throws IllegalArgumentException 유효하지 않음
     */
    public static void nonNull(Object target, String argumentName) throws IllegalArgumentException {
        if (Objects.isNull(target)) {
            throw new IllegalArgumentException(
                    String.format("%s should not be null.", argumentName));
        }
    }

    /**
     * 빈 문자열 아님을 확인
     *
     * @param target       확인 대상
     * @param argumentName 인자명
     * @throws IllegalArgumentException 유효하지 않음
     */
    public static void notEmpty(String target, String argumentName) throws IllegalArgumentException {
        if (StringUtils.isEmpty(target)) {
            throw new IllegalArgumentException(
                    String.format("%s should not be empty.", argumentName));
        }
    }

    /**
     * 숫자 최소값 확인 (null 아닌 경우만 확인)
     *
     * @param target       확인 대상
     * @param minimum      최소값 (이상)
     * @param argumentName 인자명
     * @throws IllegalArgumentException 유효하지 않음
     */
    public static void minNumber(long target, int minimum, String argumentName) throws IllegalArgumentException {
        if (Objects.nonNull(target) && target < minimum) {
            throw new IllegalArgumentException(
                    String.format("%s should be %d or more.", argumentName, minimum));
        }
    }
}
