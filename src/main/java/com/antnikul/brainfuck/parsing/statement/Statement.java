package com.antnikul.brainfuck.parsing.statement;

import com.antnikul.brainfuck.execution.BrainfuckExecutionException;
import com.antnikul.brainfuck.execution.ExecutionRuntime;
import com.antnikul.brainfuck.optimization.OptimizationStrategy;
import com.antnikul.brainfuck.parsing.expression.Expression;
import com.antnikul.brainfuck.transpilation.BrainfuckExporter;


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
     * Executes the statement and carries out certain action by making changes in {@code runtime}. It is a base
     * method that should be overridden in child classes.
     *
     * @param runtime an environment with current state of the program
     * @throws BrainfuckExecutionException if expression execution fails
     */
    public abstract void execute(ExecutionRuntime runtime) throws BrainfuckExecutionException;

    /**
     * Exports the statement using given {@code exporter} and returns a code snippet corresponding to the statement
     * functionality but written in a different programming language.
     *
     * @param exporter an exporter to convert statement to another programming language
     * @return a transpiled code snippet
     */
    public abstract String export(BrainfuckExporter exporter);

    /**
     * Optimizes internal structure of the statement using given optimization {@code strategy}.
     *
     * @param strategy an optimization algorithm
     */
    public abstract void optimize(OptimizationStrategy strategy);
}
