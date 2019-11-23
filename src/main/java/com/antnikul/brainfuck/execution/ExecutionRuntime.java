package com.antnikul.brainfuck.execution;

import java.io.IOException;
import java.io.OutputStream;

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
    private OutputStream outputStream;

    public ExecutionRuntime() {
        this(DEFAULT_ARRAY_SIZE, System.out);
    }

    public ExecutionRuntime(int cellNumber, OutputStream outputStream) {
        this.cells = new byte[cellNumber];
        this.pointer = 0;
        this.outputStream = outputStream;
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
     * @throws BrainfuckExecutionException if an pointer is moved outside array bounds
     */
    public void shiftPointer(int value) throws BrainfuckExecutionException {
        if (pointer + value < 0 || pointer + value >= cells.length) {
            throw new BrainfuckExecutionException("Instruction pointer cannot be moved outside the bounds of the " +
                    "array");
        }
        pointer += value;
    }

    /**
     * Prints bytes to the output stream.
     *
     * @param values byte values to output
     * @throws BrainfuckExecutionException if an I/O error occurs
     */
    public void print(byte... values) throws BrainfuckExecutionException {
        try {
            outputStream.write(values);
        } catch (IOException e) {
            throw new BrainfuckExecutionException("Cannot execute Brainfuck output instruction due to I/O error", e);
        }
    }
}
