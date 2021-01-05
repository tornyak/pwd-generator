package com.tornyak;

import org.apache.commons.lang3.RandomStringUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.security.SecureRandom;

@ApplicationScoped
public class PasswordService {

    @Inject
    private SecureRandom rng;

    public String generatePassword(PasswordPolicy policy) {

        if (policy.getLength() == 0) {
            return "";
        }

        StringBuilder result = new StringBuilder(policy.getLength());
        StringBuilder alphabet = new StringBuilder();

        if (policy.useDigits()) {
            result.append(RandomStringUtils.randomNumeric(1));
            alphabet.append(PasswordPolicy.DIGITS);
        }

        if (policy.useSymbols()) {
            result.append(RandomStringUtils.random(1, PasswordPolicy.SYMBOLS.toCharArray()));
            alphabet.append(PasswordPolicy.SYMBOLS);
        }

        if (policy.useLetters()) {
            result.append(RandomStringUtils.randomAlphabetic(1));
            alphabet.append(PasswordPolicy.LOWERCASE_LETTERS);
            alphabet.append(PasswordPolicy.UPPERCASE_LETTERS);
        }

        int remaining = policy.getLength() - result.length();
        result.append(RandomStringUtils.random(remaining, alphabet.toString().toCharArray()));

        shuffle(result);

        return result.substring(0, policy.getLength());
    }

    public void shuffle(StringBuilder data) {
        for (int i = 0; i < data.length() - 1; i++) {
            int j = rng.nextInt(data.length() - i) + i;
            char tmp = data.charAt(i);
            data.setCharAt(i, data.charAt(j));
            data.setCharAt(j, tmp);
        }
    }
}
