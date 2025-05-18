package com.github.laziestcoder.handsontry.train_ticket.utils;

/**
 * @author TOWFIQUL ISLAM
 * @since 26/11/24
 */

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.Objects;

@UtilityClass
@Slf4j
public class TokenStore {

    private static final long EXPIRY_TIME = 60 * 1000;

    private static String token;
    private static Instant expirationTime;

    public static String getToken() {
        if (!Objects.isNull(token) && !token.isEmpty() && expirationTime.isAfter(Instant.now())) {
            return token;
        }
        token = "";
        log.warn("token not found");
        return "";
    }

    public static void setToken(String newToken, int expiresInMinutes) {
        token = newToken;
        expirationTime = Instant.now().plusMillis(expiresInMinutes * EXPIRY_TIME); // Set expiration time
        log.info("token set successfully");
    }

    public static void clear() {
        token = "";
    }
}

