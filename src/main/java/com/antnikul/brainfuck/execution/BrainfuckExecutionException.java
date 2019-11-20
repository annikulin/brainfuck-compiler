package com.antnikul.brainfuck.execution;

/**
 * An exception signalling that Java failed to execute Brainfuck program.
 */
public class BrainfuckExecutionException extends Exception {
    public BrainfuckExecutionException(String msg) {
        super(msg);
    }

    public BrainfuckExecutionException(String message, Throwable cause) {
        super(message, cause);
    }
}
