package com.antnikul.brainfuck.execution;

/**
 * An environment where a Brainfuck program runs.
 * <p>
 * The environment represents a simple machine model consisting of a movable instruction pointer, and an array with
 * byte cells initialized to zero.
 */
public class ExecutionRuntime {
    private static final int DEFAULT_ARRAY_SIZE = 30_000;

    private int pointer;
    private byte[] cells;

    public ExecutionRuntime() {
        this(DEFAULT_ARRAY_SIZE);
    }

    public ExecutionRuntime(int cellNumber) {
        this.cells = new byte[cellNumber];
        this.pointer = 0;
    }

    /**
     * Returns the value of the "current" cell (matching the instruction pointer).
     *
     * @return the value of the "current" cell
     */
    public byte getCellValue() {
        return cells[pointer];
    }

    /**
     * Increments the value of the "current" cell by {@code value}.
     * <p>
     * If passed summand is negative, the cell gets decremented.
     *
     * @param value a byte value to be added to the "current" cell
     */
    public void incrementCellValue(byte value) {
        cells[pointer] += value;
    }

    /**
     * Shifts the instruction pointer by {@code value} number of cells.
     * <p>
     * If passed {@code value} is positive, the pointer is moved to the right, if negative - to the left.
     *
     * @param value a value the pointer to be shifted by
     */
    public void shiftPointer(int value) {
        if (pointer + value < 0 || pointer + value >= cells.length) {
            throw new BrainfuckRuntimeException("Instruction pointer cannot be moved outside the bounds of the array");
        }
        pointer += value;
    }
}
