package com.antnikul.brainfuck.execution;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExecutionRuntimeTest {

    private ExecutionRuntime executionRuntime = new ExecutionRuntime();

    @Test
    @DisplayName("Cell values should be initialized with zeros")
    void getCellValue() {
        assertEquals(0, executionRuntime.getCellValue());
    }

    @Test
    @DisplayName("Increment should change value of the current cell")
    void incrementCellValue() {
        executionRuntime.incrementCellValue((byte) 15);
        executionRuntime.incrementCellValue((byte) -4);
        assertEquals(11, executionRuntime.getCellValue());
    }

    @Test
    @DisplayName("Increment should truncate top bits in case of byte overflow")
    void incrementCellValueWithByteOverflow() {
        executionRuntime.incrementCellValue((byte) 127);
        executionRuntime.incrementCellValue((byte) 1);
        assertEquals(-128, executionRuntime.getCellValue());
    }

    @Test
    @DisplayName("Pointer should be shifted right and left depending on the passed value")
    void shiftPointer() {
        executionRuntime.incrementCellValue((byte) 5);
        executionRuntime.shiftPointer(10);
        assertEquals(0, executionRuntime.getCellValue());
        executionRuntime.shiftPointer(-10);
        assertEquals(5, executionRuntime.getCellValue());
    }

    @Test
    @DisplayName("Pointer should be shifted right and left depending on the passed value")
    void shiftPointerOutsideArrayBounds() {
        ExecutionRuntime runtime = new ExecutionRuntime(10);
        assertThrows(IllegalArgumentException.class, () -> runtime.shiftPointer(-1));
        assertThrows(IllegalArgumentException.class, () -> runtime.shiftPointer(10));
    }
}
