package com.antnikul.brainfuck.compilation.parsing.statement;

import com.antnikul.brainfuck.execution.BrainfuckExecutionException;
import com.antnikul.brainfuck.execution.ExecutionRuntime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class PrintStatementTest {

    @Test
    @DisplayName("Print statement should output byte value of the current cell to the output stream")
    void execute() throws BrainfuckExecutionException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ExecutionRuntime runtime = new ExecutionRuntime(10, outputStream);
        runtime.incrementCellValue((byte) 5);
        Statement.newPrintStatement().execute(runtime);
        assertArrayEquals(new byte[]{5}, outputStream.toByteArray());
    }
}
