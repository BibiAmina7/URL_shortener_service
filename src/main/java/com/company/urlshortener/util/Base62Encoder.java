package com.company.urlshortener.util;

public class Base62Encoder {
    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE = ALPHABET.length();

    public static String encode(long num) {
        if (num == 0) return "0";
        StringBuilder sb = new StringBuilder();
        while (num > 0) {
            int rem = (int) (num % BASE);
            sb.append(ALPHABET.charAt(rem));
            num /= BASE;
        }
        return sb.reverse().toString();
    }
}
