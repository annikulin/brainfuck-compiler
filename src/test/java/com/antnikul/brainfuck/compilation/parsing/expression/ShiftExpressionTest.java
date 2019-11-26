package com.antnikul.brainfuck.compilation.parsing.expression;

import com.antnikul.brainfuck.execution.BrainfuckExecutionException;
import com.antnikul.brainfuck.execution.ExecutionRuntime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShiftExpressionTest {

    private ExecutionRuntime runtime = new ExecutionRuntime(10, System.out);

    @BeforeEach
    void initRuntime() throws BrainfuckExecutionException {
        for (byte i = 1; i < 10; i++) {
            runtime.shiftPointer(1);
            runtime.incrementCellValue(i);
        }
        runtime.shiftPointer(-9);
    }

    @Test
    @DisplayName("Shift right expression should move data pointer to the right")
    void executeShiftRightExpression() throws BrainfuckExecutionException {
        Expression shiftRightByTwo = Expression.shiftRight(2);
        shiftRightByTwo.execute(runtime);
        assertEquals(2, runtime.getCellValue());
        shiftRightByTwo.execute(runtime);
        assertEquals(4, runtime.getCellValue());
    }

    @Test
    @DisplayName("Shift left expression should move data pointer to the left")
    void executeShiftLeftExpression() throws BrainfuckExecutionException {
        runtime.shiftPointer(9);
        Expression shiftLeftByFour = Expression.shiftLeft(4);
        shiftLeftByFour.execute(runtime);
        assertEquals(5, runtime.getCellValue());
        shiftLeftByFour.execute(runtime);
        assertEquals(1, runtime.getCellValue());
    }
}
