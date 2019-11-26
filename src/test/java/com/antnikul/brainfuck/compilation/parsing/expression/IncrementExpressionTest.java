package com.antnikul.brainfuck.compilation.parsing.expression;

import com.antnikul.brainfuck.execution.BrainfuckExecutionException;
import com.antnikul.brainfuck.execution.ExecutionRuntime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IncrementExpressionTest {

    private ExecutionRuntime runtime = new ExecutionRuntime();

    @Test
    @DisplayName("Increment expression should add value to the current cell")
    void executeIncrementByExpression() throws BrainfuckExecutionException {
        Expression incrementByFive = Expression.incrementBy((byte) 5);
        incrementByFive.execute(runtime);
        assertEquals(5, runtime.getCellValue());
    }

    @Test
    @DisplayName("Decrement expression should subtract value from the current cell")
    void executeDecrementByExpression() throws BrainfuckExecutionException {
        Expression decrementByTen = Expression.decrementBy((byte) 10);
        decrementByTen.execute(runtime);
        assertEquals(-10, runtime.getCellValue());
    }
}
