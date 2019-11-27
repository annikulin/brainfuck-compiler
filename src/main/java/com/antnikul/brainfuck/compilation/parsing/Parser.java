package com.antnikul.brainfuck.compilation.parsing;

import com.antnikul.brainfuck.compilation.BrainfuckCompilationException;
import com.antnikul.brainfuck.compilation.lexing.Token;
import com.antnikul.brainfuck.compilation.parsing.expression.Expression;
import com.antnikul.brainfuck.compilation.parsing.statement.LoopStatement;
import com.antnikul.brainfuck.compilation.parsing.statement.Statement;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static com.antnikul.brainfuck.compilation.lexing.Token.LOOP_END;
import static com.antnikul.brainfuck.compilation.lexing.Token.LOOP_START;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A part of Brainfuck compiler that analyses a sequence of {@link Token} objects and constructs a parse model showing
 * their syntactic relation to each other.
 * <p>
 * A parse model is built based on a data model consisting of {@link Expression} and {@link Statement} elements.
 * Statements express some action and should be executed sequentially. Every statement may contain one or more
 * expressions that represent Brainfuck commands and change data when being executed.
 */
public class Parser {

    private List<Statement> parseModel = new ArrayList<>();
    private Deque<LoopStatement> loopStack = new ArrayDeque<>();
    private List<Expression> exprQueue = new ArrayList<>();

    private List<Token> input;

    // private constructor to avoid initialization from outside
    private Parser(List<Token> input) {
        this.input = input;
    }

    public static Parser from(List<Token> input) {
        return new Parser(input);
    }

    /**
     * Analyses a sequence of input token and builds a parse model conforming to the rules of Brainfuck grammar.
     *
     * @return a list of statements
     * @throws BrainfuckCompilationException if parser fails to construct the parse model from the token sequence
     */
    public List<Statement> parse() throws BrainfuckCompilationException {
        checkNotNull(input);

        for (Token token : input) {
            switch (token) {
                case INCREMENT:
                    exprQueue.add(Expression.incrementBy((byte) 1));
                    break;
                case DECREMENT:
                    exprQueue.add(Expression.decrementBy((byte) 1));
                    break;
                case SHIFT_LEFT:
                    exprQueue.add(Expression.shiftLeft((byte) 1));
                    break;
                case SHIFT_RIGHT:
                    exprQueue.add(Expression.shiftRight((byte) 1));
                    break;
                case OUT:
                    addPendingExpressionsToParseModel();
                    addStatementToParseModel(Statement.newPrintStatement());
                    break;
                case LOOP_START:
                    addPendingExpressionsToParseModel();
                    loopStack.push(Statement.newLoopStatement());
                    break;
                case LOOP_END:
                    addPendingExpressionsToParseModel();
                    checkOpenLoopExists();
                    addStatementToParseModel(loopStack.pop());
                    break;
            }
        }
        checkNoOpenLoopsExist();
        addPendingExpressionsToParseModel();
        return parseModel;
    }

    private void addPendingExpressionsToParseModel() {
        if (!exprQueue.isEmpty()) {
            Statement previousStatement = Statement.newExpressionStatement(exprQueue.toArray(new Expression[0]));
            addStatementToParseModel(previousStatement);
            exprQueue.clear();
        }
    }

    private void addStatementToParseModel(Statement stmt) {
        if (loopStack.isEmpty()) {
            parseModel.add(stmt);
        } else {
            loopStack.peek().addStatement(stmt);
        }
    }

    private void checkOpenLoopExists() throws BrainfuckCompilationException {
        if (loopStack.isEmpty()) {
            String msg = String.format("Found incorrectly closed loop. Every '%s' command must be prepended by '%s'.",
                    LOOP_END.getLexeme(), LOOP_START.getLexeme());
            throw new BrainfuckCompilationException(msg);
        }
    }

    private void checkNoOpenLoopsExist() throws BrainfuckCompilationException {
        if (!loopStack.isEmpty()) {
            String msg = String.format("Found unclosed loop. Every '%s' command must be followed by '%s'.",
                    LOOP_START.getLexeme(), LOOP_END.getLexeme());
            throw new BrainfuckCompilationException(msg);
        }
    }

}
