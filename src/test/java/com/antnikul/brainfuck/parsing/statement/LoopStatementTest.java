package com.antnikul.brainfuck.parsing.statement;

import com.antnikul.brainfuck.execution.BrainfuckExecutionException;
import com.antnikul.brainfuck.execution.ExecutionRuntime;
import com.antnikul.brainfuck.parsing.expression.Expression;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoopStatementTest {

    @Test
    @DisplayName("Loop statement should execute given statements until current cell value becomes zero")
    void execute() throws BrainfuckExecutionException {
        ExecutionRuntime runtime = new ExecutionRuntime();
        runtime.incrementCellValue((byte) 10);
        Statement expressionStatement = Statement.newExpressionStatement(Expression.incrementBy((byte) 1));
        Statement loopStatement = Statement.newLoopStatement(expressionStatement);
        loopStatement.execute(runtime);
        assertEquals(0, runtime.getCellValue());
    }
}
