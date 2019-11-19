package com.antnikul.brainfuck.lexing;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TokenTest {

    @Test
    @DisplayName("valueOf should create token from character")
    void valueOf() {
        Token actualToken = Token.valueOf('>');
        assertEquals(Token.SHIFT_RIGHT, actualToken);
    }

    @Test
    @DisplayName("valueOf should throw an exception when wrong character is passed")
    void valueOfWithInvalidLexeme() {
        assertThrows(IllegalArgumentException.class, () -> Token.valueOf('%'));
    }
}
