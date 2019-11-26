package com.antnikul.brainfuck.optimization;

import com.antnikul.brainfuck.parsing.expression.Expression;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultOptimizationStrategyTest {

    private OptimizationStrategy strategy = new DefaultOptimizationStrategy();

    @Test
    @DisplayName("Subsequent shift expressions should be replaced with one")
    void optimizeSubsequentShiftExpressions() {
        List<Expression> expressions = asList(
                Expression.incrementBy((byte) 1),
                Expression.shiftLeft(2),
                Expression.shiftLeft(6),
                Expression.shiftRight(3)
        );
        List<Expression> expectedOptimizedExpressions = asList(
                Expression.incrementBy((byte) 1),
                Expression.shiftLeft(5)
        );
        assertEquals(expectedOptimizedExpressions, strategy.optimizeExpressions(expressions));
    }

    @Test
    @DisplayName("Subsequent increment expressions should be replaced with one")
    void optimizeSubsequentIncrementExpressions() {
        List<Expression> expressions = asList(
                Expression.incrementBy((byte) 1),
                Expression.decrementBy((byte) 3),
                Expression.incrementBy((byte) 10),
                Expression.shiftLeft(3)
        );
        List<Expression> expectedOptimizedExpressions = asList(
                Expression.incrementBy((byte) 8),
                Expression.shiftLeft(3)
        );
        assertEquals(expectedOptimizedExpressions, strategy.optimizeExpressions(expressions));
    }

    @Test
    @DisplayName("Subsequent expressions with zero aggregate value should be removed")
    void optimizeExpressionsWithZeroAggregateValue() {
        List<Expression> expressions = asList(
                Expression.incrementBy((byte) 2),
                Expression.decrementBy((byte) 1),
                Expression.decrementBy((byte) 1),
                Expression.shiftLeft(2),
                Expression.shiftLeft(2),
                Expression.shiftRight(4)
        );
        assertTrue(strategy.optimizeExpressions(expressions).isEmpty());
    }
}
