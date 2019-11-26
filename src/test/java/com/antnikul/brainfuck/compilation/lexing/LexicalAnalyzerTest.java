package com.antnikul.brainfuck.compilation.lexing;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LexicalAnalyzerTest {

    @Test
    @DisplayName("LexicalAnalyzer should split a sequence of input characters and convert them into tokens")
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
    @DisplayName("LexicalAnalyzer should gracefully handle empty stream")
    void tokenizeWithEmptyString() throws IOException {
        List<Token> actualTokens = LexicalAnalyzer.tokenize(new StringReader(""));
        assertTrue(actualTokens.isEmpty());
    }

    @Test
    @DisplayName("LexicalAnalyzer should ignore whitespace characters")
    void tokenizeWithWhitespaces() throws IOException {
        List<Token> actualTokens = LexicalAnalyzer.tokenize(new StringReader(">\n\t <\r"));
        List<Token> expectedTokens = Arrays.asList(Token.SHIFT_RIGHT, Token.SHIFT_LEFT);
        assertEquals(expectedTokens, actualTokens);
    }
}
