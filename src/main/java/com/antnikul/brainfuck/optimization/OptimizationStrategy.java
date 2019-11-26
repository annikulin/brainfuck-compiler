package com.antnikul.brainfuck.optimization;

import com.antnikul.brainfuck.parsing.expression.Expression;

import java.util.List;

/**
 * A base interface for optimization algorithms that analyze Brainfuck parse tree in order to improve program execution
 * time. Typically, it is done by avoiding unnecessary calculations and or merging several subsequent calculations
 * into one.
 */
public interface OptimizationStrategy {

    /**
     * Analyses a list of {@link Expression} objects and returns an optimized copy of it.
     *
     * @param expressions a list of expressions to optimize
     * @return optimized or empty list
     */
    List<Expression> optimizeExpressions(List<Expression> expressions);
}
