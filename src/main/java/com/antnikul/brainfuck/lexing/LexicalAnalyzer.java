package com.antnikul.brainfuck.lexing;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A part of Brainfuck compiler that converts a sequence of characters into a sequence of {@link Token} objects. Also
 * known as lexer or tokenizer.
 */
public class LexicalAnalyzer {

    // avoid instantiating utility class
    private LexicalAnalyzer() {
    }

    /**
     * Converts a stream of characters representing a Brainfuck program into a sequence of {@link Token} objects.
     * <p>
     * Ignores characters determined as whitespaces by {@link Character#isWhitespace(char)} method.
     *
     * @param input a source stream of characters
     * @return a list of corresponding tokens
     * @throws IOException if I/O error occurred
     */
    public static List<Token> tokenize(Reader input) throws IOException {
        checkNotNull(input);
        // random access is not needed while insertion is faster in LinkedList
        @SuppressWarnings("JdkObsolete")
        List<Token> tokens = new LinkedList<>();
        int c;
        while ((c = input.read()) != -1) {
            char character = (char) c;
            if (!Character.isWhitespace(character)) {
                tokens.add(Token.valueOf(character));
            }
        }
        return tokens;
    }
}
