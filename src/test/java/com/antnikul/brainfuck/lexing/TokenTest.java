package com.antnikul.brainfuck.lexing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TokenTest {

    @Test
    void valueOf() {
        Token actualToken = Token.valueOf('>');
        assertEquals(Token.SHIFT_RIGHT, actualToken);
    }

    @Test
    void valueOfWithInvalidLexeme() {
        assertThrows(IllegalArgumentException.class, () -> Token.valueOf('%'));
    }
}
