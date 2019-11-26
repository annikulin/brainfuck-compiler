package com.antnikul.brainfuck.execution;

import com.antnikul.brainfuck.compilation.lexing.LexicalAnalyzer;
import com.antnikul.brainfuck.compilation.lexing.Token;
import com.antnikul.brainfuck.compilation.optimization.CodeOptimizer;
import com.antnikul.brainfuck.compilation.parsing.Parser;
import com.antnikul.brainfuck.compilation.parsing.statement.Statement;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * A interpreter for Brainfuck language that processes statements written in Brainfuck language, turns them into parse
 * model, and executes in Java.
 */
public class BrainfuckJavaInterpreter {

    private ExecutionRuntime runtime;

    public BrainfuckJavaInterpreter(ExecutionRuntime runtime) {
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
