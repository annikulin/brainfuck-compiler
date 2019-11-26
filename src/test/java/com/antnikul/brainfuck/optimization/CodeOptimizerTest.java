package com.antnikul.brainfuck.optimization;

import com.antnikul.brainfuck.parsing.expression.Expression;
import com.antnikul.brainfuck.parsing.statement.Statement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

class CodeOptimizerTest {

    @Test
    @DisplayName("Nested parse model should be optimized correctly")
    void optimizeParseModel() {
        List<Statement> statements = asList(
                Statement.newPrintStatement(),
                Statement.newLoopStatement(
                        Statement.newExpressionStatement(
                                Expression.shiftRight(1),
                                Expression.incrementBy((byte) 5),
                                Expression.decrementBy((byte) 2),
                                Expression.incrementBy((byte) 1)
                        ),
                        Statement.newLoopStatement(
                                Statement.newExpressionStatement(
                                        Expression.incrementBy((byte) 5),
                                        Expression.decrementBy((byte) 5)
                                ),
                                Statement.newPrintStatement()
                        )
                )
        );
        List<Statement> expectedOptimizedStatements = asList(
                Statement.newPrintStatement(),
                Statement.newLoopStatement(
                        Statement.newExpressionStatement(
                                Expression.shiftRight(1),
                                Expression.incrementBy((byte) 4)
                        ),
                        Statement.newLoopStatement(
                                Statement.newExpressionStatement(),
                                Statement.newPrintStatement()
                        )
                )
        );
        assertEquals(expectedOptimizedStatements, CodeOptimizer.optimize(statements));
    }
}
