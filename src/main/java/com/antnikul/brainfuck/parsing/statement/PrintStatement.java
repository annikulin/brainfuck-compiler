package com.antnikul.brainfuck.parsing.statement;

import com.antnikul.brainfuck.execution.BrainfuckExecutionException;
import com.antnikul.brainfuck.execution.ExecutionRuntime;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A statement with action to print the byte value of the current cell to the output stream.
 */
class PrintStatement extends Statement {

    @Override
    public void execute(ExecutionRuntime runtime) throws BrainfuckExecutionException {
        checkNotNull(runtime);
        runtime.print(runtime.getCellValue());
    }
}
