package com.antnikul.brainfuck.compilation.parsing.expression;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExpressionTest {

    @Test
    @DisplayName("'incrementBy' factory method should create increment expression with positive value")
    void incrementBy() {
        Expression actualExpression = Expression.incrementBy((byte) 5);
        Expression expectedExpression = new IncrementExpression((byte) 5);
        assertEquals(expectedExpression, actualExpression);
    }

    @Test
    @DisplayName("Increment by a negative value should throw an exception")
    void incrementByNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> Expression.incrementBy((byte) -1));
    }

    @Test
    @DisplayName("'decrementBy' factory method should create increment expression with negative value")
    void decrementBy() {
        Expression actualExpression = Expression.decrementBy((byte) 4);
        Expression expectedExpression = new IncrementExpression((byte) -4);
        assertEquals(expectedExpression, actualExpression);
    }

    @Test
    @DisplayName("Decrement by a negative value should throw an exception")
    void decrementByNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> Expression.decrementBy((byte) -1));
    }

    @Test
    @DisplayName("'shiftRight' factory method should create shift expression with positive value")
    void shiftRight() {
        Expression actualExpression = Expression.shiftRight((byte) 6);
        Expression expectedExpression = new ShiftExpression((byte) 6);
        assertEquals(expectedExpression, actualExpression);
    }

    @Test
    @DisplayName("Shift right by a negative value should throw an exception")
    void shiftRightByNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> Expression.shiftRight((byte) -1));
    }

    @Test
    @DisplayName("'shiftLeft' factory method should create shift expression with negative value")
    void shiftLeft() {
        Expression actualExpression = Expression.shiftLeft((byte) 6);
        Expression expectedExpression = new ShiftExpression((byte) -6);
        assertEquals(expectedExpression, actualExpression);
    }

    @Test
    @DisplayName("Shift left by a negative value should throw an exception")
    void shiftLeftByNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> Expression.shiftLeft((byte) -1));
    }
}
