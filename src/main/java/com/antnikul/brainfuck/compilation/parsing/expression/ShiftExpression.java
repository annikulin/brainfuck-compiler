package com.antnikul.brainfuck.compilation.parsing.expression;

import com.antnikul.brainfuck.execution.BrainfuckExecutionException;
import com.antnikul.brainfuck.execution.ExecutionRuntime;
import com.antnikul.brainfuck.compilation.codegeneration.CodeGenerator;
import com.google.common.base.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * An {@link Expression} representing one or more shift commands that increment or decrement the data pointer in
 * Brainfuck language.
 */
public class ShiftExpression extends Expression {

    private int value;

    ShiftExpression(int value) {
        super();
        this.value = value;
    }

    @Override
    public void execute(ExecutionRuntime runtime) throws BrainfuckExecutionException {
        checkNotNull(runtime);
        runtime.shiftPointer(value);
    }

    @Override
    public String export(CodeGenerator codeGenerator) {
        return codeGenerator.generateShiftExpression(this);
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ShiftExpression)) {
            return false;
        }
        ShiftExpression that = (ShiftExpression) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
