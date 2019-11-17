package com.antnikul.brainfuck.lexing;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LexicalAnalyzerTest {

    @Test
    void tokenize() throws IOException {
        List<Token> actualTokens = LexicalAnalyzer.tokenize(new StringReader("[->+]"));
        List<Token> expectedTokens = Arrays.asList(
                Token.LOOP_START,
                Token.DECREMENT,
                Token.SHIFT_RIGHT,
                Token.INCREMENT,
                Token.LOOP_END
        );
        assertEquals(expectedTokens, actualTokens);
    }

    @Test
    void tokenizeWithEmptyString() throws IOException {
        List<Token> actualTokens = LexicalAnalyzer.tokenize(new StringReader(""));
        assertTrue(actualTokens.isEmpty());
    }

    @Test
    void tokenizeWithWhitespaces() throws IOException {
        List<Token> actualTokens = LexicalAnalyzer.tokenize(new StringReader(">\n\t <\r"));
        List<Token> expectedTokens = Arrays.asList(Token.SHIFT_RIGHT, Token.SHIFT_LEFT);
        assertEquals(expectedTokens, actualTokens);
    }
}
