package com.antnikul.brainfuck.parsing.statement;

import com.antnikul.brainfuck.execution.BrainfuckExecutionException;
import com.antnikul.brainfuck.execution.ExecutionRuntime;
import com.antnikul.brainfuck.transpilation.BrainfuckExporter;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A statement with action to print the byte value of the current cell to the output stream.
 */
public class PrintStatement extends Statement {

    private static PrintStatement instance;

    // private constructor to avoid initialization from outside
    private PrintStatement() {
        super();
    }

    static PrintStatement getInstance() {
        if (instance == null) {
            instance = new PrintStatement();
        }
        return instance;
    }

    @Override
    public void execute(ExecutionRuntime runtime) throws BrainfuckExecutionException {
        checkNotNull(runtime);
        runtime.print(runtime.getCellValue());
    }

    @Override
    public String export(BrainfuckExporter exporter) {
        return exporter.exportPrintStatement(this);
    }
}
