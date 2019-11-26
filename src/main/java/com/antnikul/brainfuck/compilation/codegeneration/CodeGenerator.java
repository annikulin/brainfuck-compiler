package com.antnikul.brainfuck.compilation.codegeneration;

import com.antnikul.brainfuck.compilation.parsing.expression.Expression;
import com.antnikul.brainfuck.compilation.parsing.expression.IncrementExpression;
import com.antnikul.brainfuck.compilation.parsing.expression.ShiftExpression;
import com.antnikul.brainfuck.compilation.parsing.statement.ExpressionStatement;
import com.antnikul.brainfuck.compilation.parsing.statement.LoopStatement;
import com.antnikul.brainfuck.compilation.parsing.statement.PrintStatement;
import com.antnikul.brainfuck.compilation.parsing.statement.Statement;

import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A base interface to convert Brainfuck parse model (consisting of {@link Statement} and {@link Expression} objects)
 * to other programming languages.
 * <p>
 * This interface is used in transpilers that take the source code of a program written in a Brainfuck language and
 * produce the equivalent source code in a different programming language.
 */
public interface CodeGenerator {

    String NEW_LINE_CONST = System.lineSeparator();
    String SEMICOLON_CONST = ";";

    /**
     * Generates a code snippet to initialize a simple machine model consisting of an instruction pointer, and an array
     * of cells initialized to zero.
     *
     * @return a code snippet with runtime initialization
     */
    String generateRuntimeInitialization();

    /**
     * Generates a code snippet matching a {@link LoopStatement} written in a target programming language.
     *
     * @param stmt loop statement to export
     * @return a code snippet executing loop statement
     */
    String generateLoopStatement(LoopStatement stmt);

    /**
     * Generates a code snippet matching a {@link PrintStatement} written in a target programming language.
     *
     * @param stmt print statement to export
     * @return a code snippet executing print statement
     */
    String generatePrintStatement(PrintStatement stmt);

    /**
     * Generates a code snippet matching an {@link IncrementExpression} written in a target programming language.
     *
     * @param exp increment expression to export
     * @return a code snippet executing increment expression
     */
    String generateIncrementExpression(IncrementExpression exp);

    /**
     * Generates a code snippet matching a {@link ShiftExpression} written in a target programming language.
     *
     * @param exp shift expression to export
     * @return a code snippet executing shift expression
     */
    String generateShiftExpression(ShiftExpression exp);

    /**
     * Generates a code snippet matching an {@link ExpressionStatement} written in a target programming language.
     *
     * @param stmt expression statement to export
     * @return a code snippet executing print statement
     */
    default String generateExpressionStatement(ExpressionStatement stmt) {
        checkNotNull(stmt);
        return stmt.getExpressions()
                .stream()
                .map(e -> e.export(this))
                .collect(Collectors.joining());
    }
}
