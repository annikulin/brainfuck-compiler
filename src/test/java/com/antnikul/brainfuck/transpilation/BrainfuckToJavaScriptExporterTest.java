package com.antnikul.brainfuck.transpilation;

import com.antnikul.brainfuck.parsing.expression.Expression;
import com.antnikul.brainfuck.parsing.statement.LoopStatement;
import com.antnikul.brainfuck.parsing.statement.Statement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BrainfuckToJavaScriptExporterTest {

    private BrainfuckExporter exporter = new BrainfuckToJavaScriptExporter();

    @Test
    @DisplayName("Exporter should initialize pointer and array with cells")
    void exportRuntimeInitialization() {
        String actualCodeSnippet = exporter.exportRuntimeInitialization();
        assertTrue(actualCodeSnippet.contains("var cells = Array.apply(null, Array(MEMORY_SIZE)).map(Number.prototype" +
                ".valueOf,0);"));
        assertTrue(actualCodeSnippet.contains("var pointer = 0;"));
    }

    @Test
    @DisplayName("Exporter should repeat operation until current cell becomes zero when exporting loop statement")
    void exportLoopStatement() {
        LoopStatement loopStatement = Statement.newLoopStatement(
                Statement.newExpressionStatement(Expression.incrementBy((byte) 10))
        );
        String actualCodeSnippet = exporter.exportLoopStatement(loopStatement);
        assertEquals("while (cells[pointer] != 0) {\n" +
                "cells[pointer] += 10;\n" +
                "}\n", actualCodeSnippet);
    }

    @Test
    @DisplayName("Exporter should print current cell value when exporting print statement")
    void exportPrintStatement() {
        String actualCodeSnippet = exporter.exportPrintStatement(Statement.newPrintStatement());
        assertEquals("print(String.fromCharCode(cells[pointer]));\n", actualCodeSnippet);
    }

    @Test
    @DisplayName("Exporter should change pointer and data array when exporting expression statement")
    void exportExpressionStatement() {
        String actualCodeSnippet = exporter.exportExpressionStatement(Statement.newExpressionStatement(
                Expression.incrementBy((byte) 3),
                Expression.shiftRight(5)
        ));
        assertEquals("cells[pointer] += 3;\n" +
                "pointer += 5;\n", actualCodeSnippet);
    }

    @Test
    @DisplayName("Exporter should change current cell value when exporting increment expression")
    void exportIncrementExpression() {
        String actualCodeSnippet = exporter.exportIncrementExpression(Expression.decrementBy((byte) 10));
        assertEquals("cells[pointer] += -10;\n", actualCodeSnippet);
    }

    @Test
    @DisplayName("Exporter should change pointer when exporting shift expression")
    void exportShiftExpression() {
        String actualCodeSnippet = exporter.exportShiftExpression(Expression.shiftLeft(11));
        assertEquals("pointer += -11;\n", actualCodeSnippet);
    }
}
