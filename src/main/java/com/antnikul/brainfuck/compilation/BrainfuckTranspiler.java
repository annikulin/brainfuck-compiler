package com.antnikul.brainfuck.compilation;

import com.antnikul.brainfuck.compilation.codegeneration.CodeGenerator;
import com.antnikul.brainfuck.compilation.lexing.LexicalAnalyzer;
import com.antnikul.brainfuck.compilation.lexing.Token;
import com.antnikul.brainfuck.compilation.optimization.CodeOptimizer;
import com.antnikul.brainfuck.compilation.parsing.Parser;
import com.antnikul.brainfuck.compilation.parsing.statement.Statement;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

/**
 * A source-to-source compiler (transpiler) that takes the source code of a program written in Brainfuck as its input
 * and produces the equivalent source code in a different programming language.
 */
public class BrainfuckTranspiler {

    private CodeGenerator codeGenerator;

    public BrainfuckTranspiler(CodeGenerator codeGenerator) {
        this.codeGenerator = codeGenerator;
    }

    /**
     * Transpiles Brainfuck program to a different programming language and writes it to the {@code output} stream.
     *
     * @param input  an input stream with Brainfuck program
     * @param output an output stream to write transpiled program
     * @throws IOException if I/O error occurred
     */
    public void transpile(Reader input, Writer output) throws IOException, BrainfuckCompilationException {
        List<Token> tokens = LexicalAnalyzer.from(input).tokenize();
        List<Statement> statements = Parser.from(tokens).parse();
        List<Statement> optimizedStatements = CodeOptimizer.from(statements).optimize();

        String initCodeSnippet = codeGenerator.generateRuntimeInitialization();
        output.write(initCodeSnippet);
        for (Statement s : optimizedStatements) {
            String codeSnippet = s.export(codeGenerator);
            output.write(codeSnippet);
        }
    }
}
