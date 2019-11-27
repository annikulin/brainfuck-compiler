package com.antnikul.brainfuck.compilation.optimization;

import com.antnikul.brainfuck.compilation.parsing.statement.Statement;

import java.util.List;

/**
 * A utility class that helps to optimize a parse tree before execution or transpilation.
 */
public class CodeOptimizer {

    private static OptimizationStrategy DEFAULT_OPT_STRATEGY = new DefaultOptimizationStrategy();

    private List<Statement> statements;
    private OptimizationStrategy strategy;

    // avoid instantiating utility class
    private CodeOptimizer(List<Statement> statements, OptimizationStrategy strategy) {
        this.statements = statements;
        this.strategy = strategy;
    }

    public static CodeOptimizer from(List<Statement> statements) {
        return from(statements, DEFAULT_OPT_STRATEGY);
    }

    public static CodeOptimizer from(List<Statement> statements, OptimizationStrategy strategy) {
        return new CodeOptimizer(statements, strategy);
    }

    public List<Statement> optimize() {
        statements.forEach(s -> s.optimize(strategy));
        return statements;
    }
}
