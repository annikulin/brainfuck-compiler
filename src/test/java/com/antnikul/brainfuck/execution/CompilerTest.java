package com.antnikul.brainfuck.execution;

import com.antnikul.brainfuck.execution.Compiler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CompilerTest {
    @Test
    void testExecuteProgram() {
        assertThrows(UnsupportedOperationException.class, () -> {
            Compiler classUnderTest = new Compiler();
            classUnderTest.executeProgram();
        });
    }
}
