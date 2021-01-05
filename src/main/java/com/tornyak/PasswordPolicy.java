package com.tornyak;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PasswordPolicy {
    public static String DIGITS = "01234567789";
    public static String SYMBOLS = "!@#£$€%&=?*";
    public static String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwkyz";
    public static String UPPERCASE_LETTERS = "abcdefghijklmnopqrstuvwkyz".toUpperCase();
    public static String LETTERS = LOWERCASE_LETTERS + UPPERCASE_LETTERS;

    private final int length;
    private final boolean useLetters;
    private final boolean useDigits;
    private final boolean useSymbols;

    public PasswordPolicy(int length, boolean useLetters, boolean useDigits, boolean useSymbols) {
        this.length = length;
        this.useLetters = useLetters;
        this.useDigits = useDigits;
        this.useSymbols = useSymbols;
    }

    @JsonProperty("length")
    public int getLength() {
        return length;
    }

    @JsonProperty("useLetters")
    public boolean useLetters() {
        return useLetters;
    }

    @JsonProperty("useDigits")
    public boolean useDigits() {
        return useDigits;
    }

    @JsonProperty("useSymbols")
    public boolean useSymbols() {
        return useSymbols;
    }
}
