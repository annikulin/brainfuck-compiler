package com.antnikul.brainfuck.compilation.lexing;

/**
 * A categorized character representing a command in Brainfuck language.
 */
public enum Token {
    SHIFT_RIGHT('>'),
    SHIFT_LEFT('<'),
    INCREMENT('+'),
    DECREMENT('-'),
    OUT('.'),
    LOOP_START('['),
    LOOP_END(']');

    private final char lexeme;

    Token(char lexeme) {
        this.lexeme = lexeme;
    }


    /**
     * Returns a {@code Token} object holding the value extracted from the given character.
     *
     * @param c the character to be parsed
     * @return an {@code Token} object holding the value of supplied character
     */
    public static Token valueOf(char c) {
        switch (c) {
            case '>':
                return SHIFT_RIGHT;
            case '<':
                return SHIFT_LEFT;
            case '+':
                return INCREMENT;
            case '-':
                return DECREMENT;
            case '.':
                return OUT;
            case '[':
                return LOOP_START;
            case ']':
                return LOOP_END;
            default:
                throw new IllegalArgumentException("Character '" + c + "' is not a valid lexeme in Brainfuck language");
        }
    }

    public char getLexeme() {
        return lexeme;
    }
}
