package com.antnikul.brainfuck.parsing;

import com.antnikul.brainfuck.lexing.Token;
import com.antnikul.brainfuck.parsing.expression.Expression;
import com.antnikul.brainfuck.parsing.statement.LoopStatement;
import com.antnikul.brainfuck.parsing.statement.Statement;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

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

    // avoid instantiating utility class
    private Parser() {
    }

    /**
     * Analyses a sequence of input token and builds a parse model conforming to the rules of Brainfuck grammar.
     *
     * @param input a sequence of input elements representing the program
     * @return a list of statements
     */
    public static List<Statement> parse(List<Token> input) {
        checkNotNull(input);

        List<Statement> returnStatements = new ArrayList<>();
        Deque<LoopStatement> loopStack = new ArrayDeque<>();
        List<Expression> expressions = new ArrayList<>();

        for (Token token : input) {
            switch (token) {
                case INCREMENT:
                    expressions.add(Expression.incrementBy((byte) 1));
                    break;
                case DECREMENT:
                    expressions.add(Expression.decrementBy((byte) 1));
                    break;
                case SHIFT_LEFT:
                    expressions.add(Expression.shiftLeft((byte) 1));
                    break;
                case SHIFT_RIGHT:
                    expressions.add(Expression.shiftRight((byte) 1));
                    break;
                case OUT:
                    completeExpressionStatementIfNeeded(returnStatements, loopStack, expressions);
                    returnStatement(returnStatements, loopStack, Statement.newPrintStatement());
                    break;
                case LOOP_START:
                    completeExpressionStatementIfNeeded(returnStatements, loopStack, expressions);
                    loopStack.push(Statement.newLoopStatement());
                    break;
                case LOOP_END:
                    completeExpressionStatementIfNeeded(returnStatements, loopStack, expressions);
                    returnStatement(returnStatements, loopStack, loopStack.pop());
                    break;
            }
        }
        completeExpressionStatementIfNeeded(returnStatements, loopStack, expressions);

        return returnStatements;
    }

    private static void completeExpressionStatementIfNeeded(List<Statement> returnStatements,
                                                            Deque<LoopStatement> statementStack,
                                                            List<Expression> expressions) {
        if (!expressions.isEmpty()) {
            Statement previousStatement = Statement.newExpressionStatement(expressions.toArray(new Expression[0]));
            returnStatement(returnStatements, statementStack, previousStatement);
            expressions.clear();
        }
    }

    private static void returnStatement(List<Statement> returnStatements, Deque<LoopStatement> statementStack,
                                        Statement statement) {
        if (statementStack.isEmpty()) {
            returnStatements.add(statement);
        } else {
            statementStack.peek().addStatement(statement);
        }
    }
}
