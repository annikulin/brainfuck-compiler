package com.antnikul.brainfuck;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class BrainfuckCompilerTest {
    @Test
    void testExecuteProgram() {
        assertThrows(UnsupportedOperationException.class, () -> {
            BrainfuckCompiler classUnderTest = new BrainfuckCompiler();
            classUnderTest.executeProgram();
        });
    }
}
