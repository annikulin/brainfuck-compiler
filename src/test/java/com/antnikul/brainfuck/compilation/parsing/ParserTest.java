package com.antnikul.brainfuck.compilation.parsing;

import com.antnikul.brainfuck.compilation.BrainfuckCompilationException;
import com.antnikul.brainfuck.compilation.lexing.Token;
import com.antnikul.brainfuck.compilation.parsing.expression.Expression;
import com.antnikul.brainfuck.compilation.parsing.statement.Statement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.antnikul.brainfuck.compilation.lexing.Token.DECREMENT;
import static com.antnikul.brainfuck.compilation.lexing.Token.INCREMENT;
import static com.antnikul.brainfuck.compilation.lexing.Token.LOOP_END;
import static com.antnikul.brainfuck.compilation.lexing.Token.LOOP_START;
import static com.antnikul.brainfuck.compilation.lexing.Token.OUT;
import static com.antnikul.brainfuck.compilation.lexing.Token.SHIFT_LEFT;
import static com.antnikul.brainfuck.compilation.lexing.Token.SHIFT_RIGHT;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParserTest {

    private static final Expression INC_EXP = Expression.incrementBy((byte) 1);
    private static final Expression DEC_EXP = Expression.decrementBy((byte) 1);
    private static final Expression SHIFT_LEFT_EXP = Expression.shiftLeft(1);
    private static final Expression SHIFT_RIGHT_EXP = Expression.shiftRight(1);

    @Test
    @DisplayName("Program with single expression statement `++>-<` should be parsed correctly")
    void parseExpressionStatement() throws BrainfuckCompilationException {
        List<Token> inputTokens = asList(INCREMENT, INCREMENT, SHIFT_RIGHT, DECREMENT, SHIFT_LEFT);
        List<Statement> expectedResult = singletonList(Statement.newExpressionStatement(INC_EXP, INC_EXP,
                SHIFT_RIGHT_EXP, DEC_EXP, SHIFT_LEFT_EXP));
        assertEquals(expectedResult, Parser.parse(inputTokens));
    }

    @Test
    @DisplayName("Program without loops `+.>-.<.` should be parsed correctly")
    void parseStatementsWithoutLoops() throws BrainfuckCompilationException {
        List<Token> inputTokens = asList(INCREMENT, OUT, SHIFT_RIGHT, DECREMENT, OUT, SHIFT_LEFT, OUT);
        List<Statement> expectedResult = asList(
                Statement.newExpressionStatement(INC_EXP),
                Statement.newPrintStatement(),
                Statement.newExpressionStatement(SHIFT_RIGHT_EXP, DEC_EXP),
                Statement.newPrintStatement(),
                Statement.newExpressionStatement(SHIFT_LEFT_EXP),
                Statement.newPrintStatement()
        );
        assertEquals(expectedResult, Parser.parse(inputTokens));
    }

    @Test
    @DisplayName("Program with loops `+[>-]-[+.]` should be parsed correctly")
    void parseStatementsWithLoops() throws BrainfuckCompilationException {
        List<Token> inputTokens = asList(INCREMENT, LOOP_START, SHIFT_RIGHT, DECREMENT, LOOP_END, DECREMENT,
                LOOP_START, INCREMENT, OUT, LOOP_END);
        List<Statement> expectedResult = asList(
                Statement.newExpressionStatement(INC_EXP),
                Statement.newLoopStatement(Statement.newExpressionStatement(SHIFT_RIGHT_EXP, DEC_EXP)),
                Statement.newExpressionStatement(DEC_EXP),
                Statement.newLoopStatement(Statement.newExpressionStatement(INC_EXP), Statement.newPrintStatement())
        );
        assertEquals(expectedResult, Parser.parse(inputTokens));
    }

    @Test
    @DisplayName("Program with nested loops `+[>+[>+.<-]>[<]].` should be parsed correctly")
    void parseStatementsWithNestedLoops() throws BrainfuckCompilationException {
        List<Token> inputTokens = asList(INCREMENT, LOOP_START, SHIFT_RIGHT, INCREMENT, LOOP_START, SHIFT_RIGHT,
                INCREMENT, OUT, SHIFT_LEFT, DECREMENT, LOOP_END, SHIFT_RIGHT, LOOP_START, SHIFT_LEFT, LOOP_END,
                LOOP_END, OUT);
        List<Statement> expectedResult = asList(
                Statement.newExpressionStatement(INC_EXP),
                Statement.newLoopStatement(
                        Statement.newExpressionStatement(SHIFT_RIGHT_EXP, INC_EXP),
                        Statement.newLoopStatement(
                                Statement.newExpressionStatement(SHIFT_RIGHT_EXP, INC_EXP),
                                Statement.newPrintStatement(),
                                Statement.newExpressionStatement(SHIFT_LEFT_EXP, DEC_EXP)
                        ),
                        Statement.newExpressionStatement(SHIFT_RIGHT_EXP),
                        Statement.newLoopStatement(Statement.newExpressionStatement(SHIFT_LEFT_EXP))
                ),
                Statement.newPrintStatement()
        );
        assertEquals(expectedResult, Parser.parse(inputTokens));
    }

    @Test
    @DisplayName("Program with loops only `[[]][]` should be parsed correctly")
    void parseStatementsWithLoopsOnly() throws BrainfuckCompilationException {
        List<Token> inputTokens = asList(LOOP_START, LOOP_START, LOOP_END, LOOP_END, LOOP_START, LOOP_END);
        List<Statement> expectedResult = asList(
                Statement.newLoopStatement(Statement.newLoopStatement()),
                Statement.newLoopStatement()
        );
        assertEquals(expectedResult, Parser.parse(inputTokens));
    }

    @Test
    @DisplayName("Program should throw an exception when not all loops are closed `[[]][`")
    void parseStatementsWithUnclosedLoop() {
        List<Token> inputTokens = asList(LOOP_START, LOOP_START, LOOP_END, LOOP_END, LOOP_START);
        BrainfuckCompilationException thrown = assertThrows(BrainfuckCompilationException.class,
                () -> Parser.parse(inputTokens));
        assertTrue(thrown.getMessage().contains("Found unclosed loop"));
    }

    @Test
    @DisplayName("Program should throw an exception when loops closes without being opened `[[]]]`")
    void parseStatementsWithIncorrectlyClosedLoop() {
        List<Token> inputTokens = asList(LOOP_START, LOOP_START, LOOP_END, LOOP_END, LOOP_END);
        BrainfuckCompilationException thrown = assertThrows(BrainfuckCompilationException.class,
                () -> Parser.parse(inputTokens));
        assertTrue(thrown.getMessage().contains("Found incorrectly closed loop"));
    }
}
