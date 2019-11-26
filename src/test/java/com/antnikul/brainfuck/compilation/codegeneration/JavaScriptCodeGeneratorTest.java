package com.antnikul.brainfuck.compilation.codegeneration;

import com.antnikul.brainfuck.compilation.parsing.expression.Expression;
import com.antnikul.brainfuck.compilation.parsing.statement.LoopStatement;
import com.antnikul.brainfuck.compilation.parsing.statement.Statement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JavaScriptCodeGeneratorTest {

    private CodeGenerator codeGenerator = new JavaScriptCodeGenerator();

    @Test
    @DisplayName("Generator should initialize pointer and array with cells")
    void generateRuntimeInitialization() {
        String actualCodeSnippet = codeGenerator.generateRuntimeInitialization();
        assertTrue(actualCodeSnippet.contains("var cells = Array.apply(null, Array(MEMORY_SIZE)).map(Number.prototype" +
                ".valueOf,0);"));
        assertTrue(actualCodeSnippet.contains("var pointer = 0;"));
    }

    @Test
    @DisplayName("Generator should repeat operation until current cell becomes zero when exporting loop statement")
    void generateLoopStatement() {
        LoopStatement loopStatement = Statement.newLoopStatement(
                Statement.newExpressionStatement(Expression.incrementBy((byte) 10))
        );
        String actualCodeSnippet = codeGenerator.generateLoopStatement(loopStatement);
        assertEquals("while (cells[pointer] != 0) {\n" +
                "cells[pointer] += 10;\n" +
                "}\n", actualCodeSnippet);
    }

    @Test
    @DisplayName("Generator should print current cell value when exporting print statement")
    void generatePrintStatement() {
        String actualCodeSnippet = codeGenerator.generatePrintStatement(Statement.newPrintStatement());
        assertEquals("print(String.fromCharCode(cells[pointer]));\n", actualCodeSnippet);
    }

    @Test
    @DisplayName("Generator should change pointer and data array when exporting expression statement")
    void generateExpressionStatement() {
        String actualCodeSnippet = codeGenerator.generateExpressionStatement(Statement.newExpressionStatement(
                Expression.incrementBy((byte) 3),
                Expression.shiftRight(5)
        ));
        assertEquals("cells[pointer] += 3;\n" +
                "pointer += 5;\n", actualCodeSnippet);
    }

    @Test
    @DisplayName("Generator should change current cell value when exporting increment expression")
    void generateIncrementExpression() {
        String actualCodeSnippet = codeGenerator.generateIncrementExpression(Expression.decrementBy((byte) 10));
        assertEquals("cells[pointer] += -10;\n", actualCodeSnippet);
    }

    @Test
    @DisplayName("Generator should change pointer when exporting shift expression")
    void generateShiftExpression() {
        String actualCodeSnippet = codeGenerator.generateShiftExpression(Expression.shiftLeft(11));
        assertEquals("pointer += -11;\n", actualCodeSnippet);
    }
}
