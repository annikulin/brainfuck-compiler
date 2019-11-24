package com.antnikul.brainfuck.parsing.statement;

import com.antnikul.brainfuck.execution.BrainfuckExecutionException;
import com.antnikul.brainfuck.execution.ExecutionRuntime;
import com.antnikul.brainfuck.parsing.expression.Expression;
import com.antnikul.brainfuck.transpilation.BrainfuckExporter;
import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A statement with action to execute one or more {@link Expression} objects sequentially.
 */
public class ExpressionStatement extends Statement {

    private List<Expression> expressions;

    ExpressionStatement(List<Expression> expressions) {
        super();
        this.expressions = new ArrayList<>(expressions);
    }

    @Override
    public void execute(ExecutionRuntime runtime) throws BrainfuckExecutionException {
        checkNotNull(runtime);
        for (Expression e : expressions) {
            e.execute(runtime);
        }
    }

    @Override
    public String export(BrainfuckExporter exporter) {
        return exporter.exportExpressionStatement(this);
    }

    public List<Expression> getExpressions() {
        return expressions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExpressionStatement)) {
            return false;
        }
        ExpressionStatement statement = (ExpressionStatement) o;
        return Objects.equal(expressions, statement.expressions);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(expressions);
    }
}
