package com.antnikul.brainfuck.parsing.statement;

import com.antnikul.brainfuck.execution.BrainfuckExecutionException;
import com.antnikul.brainfuck.execution.ExecutionRuntime;
import com.antnikul.brainfuck.parsing.expression.Expression;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * An statement with action to execute one or more {@link Expression} objects sequentially.
 */
class ExpressionStatement extends Statement {

    private List<Expression> expressions;

    ExpressionStatement(List<Expression> expressions) {
        super();
        this.expressions = expressions;
    }

    @Override
    public void execute(ExecutionRuntime runtime) throws BrainfuckExecutionException {
        checkNotNull(runtime);
        for (Expression e : expressions) {
            e.execute(runtime);
        }
    }
}
