package com.antnikul.brainfuck.parsing.statement;

import com.antnikul.brainfuck.execution.BrainfuckExecutionException;
import com.antnikul.brainfuck.execution.ExecutionRuntime;
import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A statement with action that must be executed in a loop. The loop repeats until value of the current cell
 * becomes zero.
 */
public class LoopStatement extends Statement {

    private List<Statement> statements;

    LoopStatement(List<Statement> statements) {
        super();
        this.statements = new ArrayList<>(statements);
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

    public void addStatement(Statement statement) {
        this.statements.add(statement);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LoopStatement)) {
            return false;
        }
        LoopStatement that = (LoopStatement) o;
        return Objects.equal(statements, that.statements);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(statements);
    }
}
