package com.gympulse.app.config;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtUtilTest {

    @Test
    void initShouldSupportShortSecretsByDerivingASecureKey() {
        JwtUtil jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secret", "DEV_JWT_SECRET_CHANGE_ME");
        ReflectionTestUtils.setField(jwtUtil, "expiration", 86_400_000L);

        jwtUtil.init();

        String token = jwtUtil.generateToken("admin@company.com", "ADMIN");

        assertTrue(jwtUtil.validateToken(token));
        assertEquals("admin@company.com", jwtUtil.extractEmail(token));
        assertEquals("ADMIN", jwtUtil.extractRole(token));
    }

    @Test
    void initShouldFailWhenSecretIsMissing() {
        JwtUtil jwtUtil = new JwtUtil();
        ReflectionTestUtils.setField(jwtUtil, "secret", "   ");
        ReflectionTestUtils.setField(jwtUtil, "expiration", 86_400_000L);

        try {
            jwtUtil.init();
        } catch (IllegalStateException ex) {
            assertEquals("jwt.secret must be configured", ex.getMessage());
            return;
        }

        throw new AssertionError("Expected init() to fail when jwt.secret is blank");
    }
}
