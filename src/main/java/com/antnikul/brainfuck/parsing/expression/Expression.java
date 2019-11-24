package com.antnikul.brainfuck.parsing.expression;

import com.antnikul.brainfuck.execution.BrainfuckExecutionException;
import com.antnikul.brainfuck.execution.ExecutionRuntime;
import com.antnikul.brainfuck.transpilation.BrainfuckExporter;

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
    public static IncrementExpression incrementBy(byte value) {
        checkArgument(value >= 0, "Cell cannot be incremented by a negative value");
        return new IncrementExpression(value);
    }

    /**
     * Creates an expression decrementing "current" cell by {@code value}.
     *
     * @param value a value to be subtracted from the "current" cell
     * @return a constructed expression
     */
    public static IncrementExpression decrementBy(byte value) {
        checkArgument(value >= 0, "Cell cannot be decremented by a negative value");
        return new IncrementExpression((byte) -value);
    }

    /**
     * Creates an expression shifting the instruction pointer by {@code value} number of cells to the right.
     *
     * @param value a number of cells by which to shift the pointer
     * @return a constructed expression
     */
    public static ShiftExpression shiftRight(int value) {
        checkArgument(value >= 0, "Data pointer cannot be moved right by a negative value");
        return new ShiftExpression(value);
    }

    /**
     * Creates an expression shifting the instruction pointer by {@code value} number of cells to the left.
     *
     * @param value a number of cells by which to shift the pointer
     * @return a constructed expression
     */
    public static ShiftExpression shiftLeft(int value) {
        checkArgument(value >= 0, "Data pointer cannot be moved left by a negative value");
        return new ShiftExpression(-value);
    }

    /**
     * Executes the expression by making changes in {@code runtime}. It is a base method that should be overridden in
     * child classes.
     *
     * @param runtime an environment with current state of the program
     * @throws BrainfuckExecutionException if expression execution fails
     */
    public abstract void execute(ExecutionRuntime runtime) throws BrainfuckExecutionException;

    /**
     * Exports the expression using given {@code exporter} and returns a code snippet corresponding to the expression
     * functionality but written in a different programming language.
     *
     * @param exporter an exporter to convert expression to another programming language
     * @return a transpiled code snippet
     */
    public abstract String export(BrainfuckExporter exporter);
}
