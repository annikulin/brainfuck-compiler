package com.antnikul.brainfuck.parsing.statement;

import com.antnikul.brainfuck.execution.BrainfuckExecutionException;
import com.antnikul.brainfuck.execution.ExecutionRuntime;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A statement with action that must be executed in a loop. The loop repeats until value of the current cell
 * becomes zero.
 */
class LoopStatement extends Statement {

    private List<Statement> statements;

    LoopStatement(List<Statement> statements) {
        super();
        this.statements = statements;
    }

    @Override
    public void execute(ExecutionRuntime runtime) throws BrainfuckExecutionException {
        checkNotNull(runtime);
        while (runtime.getCellValue() != 0) {
            for (Statement s : statements) {
                s.execute(runtime);
            }
        }
    }
}
