package com.antnikul.brainfuck;

import org.junit.Test;

public class BrainfuckCompilerTest {
    @Test(expected = UnsupportedOperationException.class)
    public void testExecuteProgram() {
        BrainfuckCompiler classUnderTest = new BrainfuckCompiler();
        classUnderTest.executeProgram();
    }
}
