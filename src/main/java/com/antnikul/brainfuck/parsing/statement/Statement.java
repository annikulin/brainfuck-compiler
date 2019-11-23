package com.antnikul.brainfuck.parsing.statement;

import com.antnikul.brainfuck.execution.BrainfuckExecutionException;
import com.antnikul.brainfuck.execution.ExecutionRuntime;
import com.antnikul.brainfuck.parsing.expression.Expression;


import static java.util.Arrays.asList;

/**
 * A base class representing the smallest standalone element of an imperative programming language that expresses
 * some action to be carried out.
 */
public abstract class Statement {

    public static ExpressionStatement newExpressionStatement(Expression... expressions) {
        return new ExpressionStatement(asList(expressions));
    }

    public static LoopStatement newLoopStatement(Statement... statements) {
        return new LoopStatement(asList(statements));
    }

    public static PrintStatement newPrintStatement() {
        return PrintStatement.getInstance();
    }

    /**
     * A base method to be overridden in child classes that executes the statement and carries out certain action by
     * making changes in {@code runtime}.
     *
     * @param runtime an environment with current state of the program
     * @throws BrainfuckExecutionException if expression execution fails
     */
    public abstract void execute(ExecutionRuntime runtime) throws BrainfuckExecutionException;
}
