package com.learn.auth.jwt.jwttokenauthorization.enums;

public enum YesNo{
    YES('Y'),
    NO('N');

    private final char value;

    YesNo(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }
}
