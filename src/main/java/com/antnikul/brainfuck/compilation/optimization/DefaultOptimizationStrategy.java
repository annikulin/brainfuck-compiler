package com.antnikul.brainfuck.compilation.optimization;

import com.antnikul.brainfuck.compilation.parsing.expression.Expression;
import com.antnikul.brainfuck.compilation.parsing.expression.IncrementExpression;
import com.antnikul.brainfuck.compilation.parsing.expression.ShiftExpression;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * An optimization strategy that merges subsequent {@link IncrementExpression} and {@link ShiftExpression} objects
 * into one. If the merge produces an expression with zero value, the whole expression sequence is removed.
 */
public class DefaultOptimizationStrategy implements OptimizationStrategy {

    @Override
    public List<Expression> optimizeExpressions(List<Expression> expressions) {
        checkNotNull(expressions);

        Deque<Expression> exprQueue = new ArrayDeque<>();
        expressions.forEach(expr -> {
            Expression prevExpr = exprQueue.peekLast();
            if (prevExpr instanceof IncrementExpression && expr instanceof IncrementExpression) {
                IncrementExpression prevIncrementExpr = (IncrementExpression) exprQueue.pollLast();
                mergeIncrementExpressions(prevIncrementExpr, (IncrementExpression) expr).ifPresent(exprQueue::add);
            } else if (prevExpr instanceof ShiftExpression && expr instanceof ShiftExpression) {
                ShiftExpression prevShiftExpr = (ShiftExpression) exprQueue.pollLast();
                mergeShiftExpressions(prevShiftExpr, (ShiftExpression) expr).ifPresent(exprQueue::add);
            } else {
                exprQueue.add(expr);
            }
        });
        return new ArrayList<>(exprQueue);
    }


    private Optional<ShiftExpression> mergeShiftExpressions(ShiftExpression expr1, ShiftExpression expr2) {
        int sum = expr1.getValue() + expr2.getValue();
        if (sum != 0) {
            return Optional.of(sum < 0 ? Expression.shiftLeft(Math.abs(sum)) : Expression.shiftRight(sum));
        } else {
            return Optional.empty();
        }
    }

    private Optional<IncrementExpression> mergeIncrementExpressions(IncrementExpression expr1,
                                                                    IncrementExpression expr2) {
        byte sum = (byte) (expr1.getValue() + expr2.getValue());
        if (sum != 0) {
            return Optional.of(sum < 0 ? Expression.decrementBy((byte) Math.abs(sum)) : Expression.incrementBy(sum));
        } else {
            return Optional.empty();
        }
    }

}
