package com.antnikul.brainfuck.compilation;

/**
 * An exception signalling that Java failed to compile or transpile Brainfuck program. Typically, this exception is
 * thrown during parsing phase when tokens are arranged incorrectly.
 */
public class BrainfuckCompilationException extends Exception {
    public BrainfuckCompilationException(String message) {
        super(message);
    }
}
