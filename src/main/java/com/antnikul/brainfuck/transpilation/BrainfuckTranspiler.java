package com.antnikul.brainfuck.transpilation;

import com.antnikul.brainfuck.lexing.LexicalAnalyzer;
import com.antnikul.brainfuck.lexing.Token;
import com.antnikul.brainfuck.parsing.Parser;
import com.antnikul.brainfuck.parsing.statement.Statement;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

/**
 * A source-to-source compiler (transpiler) that takes the source code of a program written in Brainfuck as its input
 * and produces the equivalent source code in a different programming language.
 */
public class BrainfuckTranspiler {

    private BrainfuckExporter exporter;

    public BrainfuckTranspiler(BrainfuckExporter exporter) {
        this.exporter = exporter;
    }

    /**
     * Transpiles Brainfuck program to a different programming language and writes it to the {@code output} stream.
     *
     * @param input  an input stream with Brainfuck program
     * @param output an output stream to write transpiled program
     * @throws IOException if I/O error occurred
     */
    public void transpile(Reader input, Writer output) throws IOException {
        List<Token> tokens = LexicalAnalyzer.tokenize(input);
        List<Statement> statements = Parser.parse(tokens);

        String initCodeSnippet = exporter.exportRuntimeInitialization();
        output.write(initCodeSnippet);

        for (Statement s : statements) {
            String codeSnippet = s.export(exporter);
            output.write(codeSnippet);
        }
    }
}
