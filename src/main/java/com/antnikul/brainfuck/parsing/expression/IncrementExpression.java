package com.antnikul.brainfuck.parsing.expression;

import com.antnikul.brainfuck.execution.ExecutionRuntime;
import com.google.common.base.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * An {@link Expression} representing one or more increment or decrement commands in Brainfuck language.
 */
class IncrementExpression extends Expression {

    private byte value;

    IncrementExpression(byte value) {
        super();
        this.value = value;
    }

    @Override
    public void execute(ExecutionRuntime runtime) {
        checkNotNull(runtime);
        runtime.incrementCellValue(this.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IncrementExpression)) {
            return false;
        }
        IncrementExpression that = (IncrementExpression) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
