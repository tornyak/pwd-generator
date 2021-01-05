package com.tornyak;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.google.common.math.IntMath.factorial;
import static com.tornyak.PasswordPolicy.*;
import static org.apache.commons.lang3.StringUtils.containsAny;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class PasswordServiceTest {

    @Inject
    private PasswordService pwdService;
    private PasswordPolicy policy = new PasswordPolicy(8, true, true, true);

    @Test
    void generatePassword() {
        String pwd = pwdService.generatePassword(policy);
        assertEquals(pwd.length(), 8);
        assertTrue(containsAny(pwd, LOWERCASE_LETTERS));
        assertTrue(containsAny(pwd, SYMBOLS));
        assertTrue(containsAny(pwd, DIGITS));
    }

    @Test
    void passwordsAreUnique() {
        Set<String> passwords = new HashSet<>();
        for (int i = 0; i < 10000; i++) {
            String pwd = pwdService.generatePassword(policy);
            assertFalse(passwords.contains(pwd));
            passwords.add(pwd);
        }
    }

    @Test
    void passwordOfDigits() {
        PasswordPolicy p = new PasswordPolicy(8, false, true, false);
        String pwd = pwdService.generatePassword(p);
        for (int i = 0; i < pwd.length(); i++) {
            assertTrue(DIGITS.indexOf(pwd.charAt(i)) >= 0);
        }
    }

    @Test
    void passwordOfLetters() {
        PasswordPolicy p = new PasswordPolicy(8, true, false, false);
        String pwd = pwdService.generatePassword(p);
        for (int i = 0; i < pwd.length(); i++) {
            assertTrue(LETTERS.indexOf(pwd.charAt(i)) >= 0);
        }
    }

    @Test
    void passwordOfSymbols() {
        PasswordPolicy p = new PasswordPolicy(8, false, false, true);
        String pwd = pwdService.generatePassword(p);
        for (int i = 0; i < pwd.length(); i++) {
            assertTrue(SYMBOLS.indexOf(pwd.charAt(i)) >= 0);
        }
    }

    @Test
    void shuffle() {
        String data = "12345";
        final int shuffleCount = 120000;
        final int expectedCount = shuffleCount / factorial(data.length());
        final double delta = 0.15 * expectedCount;
        Map<String, Integer> counts = new HashMap<>();

        for (int i = 0; i < shuffleCount; i++) {
            StringBuilder toShuffle = new StringBuilder(data);
            pwdService.shuffle(toShuffle);
            counts.merge(toShuffle.toString(), 1, (i1, i2) -> i1 + 1);
        }

        assertEquals(120, counts.size()); // 120 is 5!

        int totalCnt = 0;
        for (var e : counts.entrySet()) {
            assertEquals(expectedCount, e.getValue(), delta, "v is out of range: " + e.getValue() + " for: " + e.getKey());
            totalCnt += e.getValue();
        }

        assertEquals(shuffleCount, totalCnt);
    }
}