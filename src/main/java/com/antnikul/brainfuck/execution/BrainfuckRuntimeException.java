package com.antnikul.brainfuck.execution;

/**
 * Signals that Java failed to execute Brainfuck program.
 */
public class BrainfuckRuntimeException extends RuntimeException {
    public BrainfuckRuntimeException(String msg) {
        super(msg);
    }
}
