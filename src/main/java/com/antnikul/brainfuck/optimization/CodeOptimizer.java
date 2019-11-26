package com.antnikul.brainfuck.optimization;

import com.antnikul.brainfuck.parsing.statement.Statement;

import java.util.List;

/**
 * A utility class that helps to optimize a parse tree before execution or transpilation.
 */
public class CodeOptimizer {

    private static OptimizationStrategy DEFAULT_OPT_STRATEGY = new DefaultOptimizationStrategy();

    // avoid instantiating utility class
    private CodeOptimizer() {
    }

    public static List<Statement> optimize(List<Statement> statements) {
        return optimize(statements, DEFAULT_OPT_STRATEGY);
    }

    public static List<Statement> optimize(List<Statement> statements, OptimizationStrategy strategy) {
        statements.forEach(s -> s.optimize(strategy));
        return statements;
    }
}
