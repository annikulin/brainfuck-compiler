package com.antnikul.brainfuck.parsing;

import com.antnikul.brainfuck.lexing.Token;
import com.antnikul.brainfuck.parsing.expression.Expression;
import com.antnikul.brainfuck.parsing.statement.Statement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.antnikul.brainfuck.lexing.Token.DECREMENT;
import static com.antnikul.brainfuck.lexing.Token.INCREMENT;
import static com.antnikul.brainfuck.lexing.Token.LOOP_END;
import static com.antnikul.brainfuck.lexing.Token.LOOP_START;
import static com.antnikul.brainfuck.lexing.Token.OUT;
import static com.antnikul.brainfuck.lexing.Token.SHIFT_LEFT;
import static com.antnikul.brainfuck.lexing.Token.SHIFT_RIGHT;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserTest {

    private static final Expression INC_EXP = Expression.incrementBy((byte) 1);
    private static final Expression DEC_EXP = Expression.decrementBy((byte) 1);
    private static final Expression SHIFT_LEFT_EXP = Expression.shiftLeft(1);
    private static final Expression SHIFT_RIGHT_EXP = Expression.shiftRight(1);

    @Test
    @DisplayName("Program with single expression statement `++>-<` should be parsed correctly")
    void parseExpressionStatement() {
        List<Token> inputTokens = asList(INCREMENT, INCREMENT, SHIFT_RIGHT, DECREMENT, SHIFT_LEFT);
        List<Statement> expectedResult = singletonList(Statement.newExpressionStatement(INC_EXP, INC_EXP,
                SHIFT_RIGHT_EXP, DEC_EXP, SHIFT_LEFT_EXP));
        assertEquals(expectedResult, Parser.parse(inputTokens));
    }

    @Test
    @DisplayName("Program without loops `+.>-.<.` should be parsed correctly")
    void parseStatementsWithoutLoops() {
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
    void parseStatementsWithLoops() {
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
    void parseStatementsWithNestedLoops() {
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
    void parseStatementsWithLoopsOnly() {
        List<Token> inputTokens = asList(LOOP_START, LOOP_START, LOOP_END, LOOP_END, LOOP_START, LOOP_END);
        List<Statement> expectedResult = asList(
                Statement.newLoopStatement(Statement.newLoopStatement()),
                Statement.newLoopStatement()
        );
        assertEquals(expectedResult, Parser.parse(inputTokens));
    }
}
