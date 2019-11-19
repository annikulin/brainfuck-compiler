package com.antnikul.brainfuck.parsing.expression;

import com.antnikul.brainfuck.execution.ExecutionRuntime;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * A base class representing a combination of one or more Brainfuck commands that the compiler executes to produce
 * another value.
 */
public abstract class Expression {

    /**
     * Creates an expression incrementing "current" cell by {@code value}.
     *
     * @param value a value to be added to the "current" cell
     * @return a constructed expression
     */
    public static Expression incrementBy(byte value) {
        checkArgument(value >= 0, "Cell cannot be incremented by a negative value");
        return new IncrementExpression(value);
    }

    /**
     * Creates an expression decrementing "current" cell by {@code value}.
     *
     * @param value a value to be subtracted from the "current" cell
     * @return a constructed expression
     */
    public static Expression decrementBy(byte value) {
        checkArgument(value >= 0, "Cell cannot be decremented by a negative value");
        return new IncrementExpression((byte) -value);
    }

    /**
     * Creates an expression shifting the instruction pointer by {@code value} number of cells to the right.
     *
     * @param value a number of cells by which to shift the pointer
     * @return a constructed expression
     */
    public static Expression shiftRight(int value) {
        checkArgument(value >= 0, "Data pointer cannot be moved right by a negative value");
        return new ShiftExpression(value);
    }

    /**
     * Creates an expression shifting the instruction pointer by {@code value} number of cells to the left.
     *
     * @param value a number of cells by which to shift the pointer
     * @return a constructed expression
     */
    public static Expression shiftLeft(int value) {
        checkArgument(value >= 0, "Data pointer cannot be moved left by a negative value");
        return new ShiftExpression(-value);
    }

    /**
     * A base method to be overridden in child classes that executes the expression by making changes in {@code
     * runtime}.
     *
     * @param runtime an environment with current state of the program
     */
    public abstract void execute(ExecutionRuntime runtime);
}
