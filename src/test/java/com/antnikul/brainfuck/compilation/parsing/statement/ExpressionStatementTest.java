package com.antnikul.brainfuck.compilation.parsing.statement;

import com.antnikul.brainfuck.execution.BrainfuckExecutionException;
import com.antnikul.brainfuck.execution.ExecutionRuntime;
import com.antnikul.brainfuck.compilation.parsing.expression.Expression;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpressionStatementTest {

    @Test
    @DisplayName("Statement should execute passed expressions one by one")
    void execute() throws BrainfuckExecutionException {
        ExecutionRuntime runtime = new ExecutionRuntime();
        Statement statement = Statement.newExpressionStatement(
                Expression.incrementBy((byte) 5),
                Expression.decrementBy((byte) 1),
                Expression.incrementBy((byte) 3)
        );
        statement.execute(runtime);
        assertEquals(7, runtime.getCellValue());
    }
}
