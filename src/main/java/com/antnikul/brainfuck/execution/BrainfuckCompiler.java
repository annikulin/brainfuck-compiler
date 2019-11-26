package com.antnikul.brainfuck.execution;

import com.antnikul.brainfuck.lexing.LexicalAnalyzer;
import com.antnikul.brainfuck.lexing.Token;
import com.antnikul.brainfuck.optimization.CodeOptimizer;
import com.antnikul.brainfuck.parsing.Parser;
import com.antnikul.brainfuck.parsing.statement.Statement;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * A compiler for Brainfuck language that processes statements written in Brainfuck language, turns them into parse
 * model, and executes in Java.
 */
public class BrainfuckCompiler {

    private ExecutionRuntime runtime;

    public BrainfuckCompiler(ExecutionRuntime runtime) {
        this.runtime = runtime;
    }

    /**
     * Compiles and executes received Brainfuck commands.
     *
     * @param input a stream of Brainfuck commands to execute
     * @throws IOException                 if I/O error occurred
     * @throws BrainfuckExecutionException if program execution failed
     */
    public void run(Reader input) throws IOException, BrainfuckExecutionException {
        List<Token> tokens = LexicalAnalyzer.tokenize(input);
        List<Statement> statements = Parser.parse(tokens);
        List<Statement> optimizedStatements = CodeOptimizer.optimize(statements);
        for (Statement s : optimizedStatements) {
            s.execute(runtime);
        }
    }
}
