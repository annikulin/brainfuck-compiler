package com.antnikul.brainfuck.compilation.codegeneration;

import com.antnikul.brainfuck.compilation.parsing.expression.IncrementExpression;
import com.antnikul.brainfuck.compilation.parsing.expression.ShiftExpression;
import com.antnikul.brainfuck.compilation.parsing.statement.LoopStatement;
import com.antnikul.brainfuck.compilation.parsing.statement.PrintStatement;

import java.util.StringJoiner;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A code generator that constructs JavaScript code snippets matching expressions and statements in Brainfuck language.
 */
public class JavaScriptCodeGenerator implements CodeGenerator {
    @Override
    public String generateRuntimeInitialization() {
        StringJoiner output = new StringJoiner(NEW_LINE_CONST, "", NEW_LINE_CONST);
        output.add("var MEMORY_SIZE = 30000" + SEMICOLON_CONST);
        output.add("var cells = Array.apply(null, Array(MEMORY_SIZE)).map(Number.prototype.valueOf,0)" + SEMICOLON_CONST);
        output.add("var pointer = 0" + SEMICOLON_CONST);
        return output.toString();
    }

    @Override
    public String generateLoopStatement(LoopStatement stmt) {
        checkNotNull(stmt);
        String statementStr = stmt.getStatements()
                .stream()
                .map(s -> s.export(this))
                .collect(Collectors.joining());
        return "while (cells[pointer] != 0) {" + NEW_LINE_CONST + statementStr + "}" + NEW_LINE_CONST;
    }

    @Override
    public String generatePrintStatement(PrintStatement stmt) {
        checkNotNull(stmt);
        return "print(String.fromCharCode(cells[pointer]))" + SEMICOLON_CONST + NEW_LINE_CONST;
    }

    @Override
    public String generateIncrementExpression(IncrementExpression exp) {
        checkNotNull(exp);
        return "cells[pointer] += " + exp.getValue() + SEMICOLON_CONST + NEW_LINE_CONST;
    }

    @Override
    public String generateShiftExpression(ShiftExpression exp) {
        checkNotNull(exp);
        return "pointer += " + exp.getValue() + SEMICOLON_CONST + NEW_LINE_CONST;
    }
}
