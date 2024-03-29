package com.antnikul.brainfuck.execution;

import com.antnikul.brainfuck.compilation.BrainfuckCompilationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BrainfuckJavaInterpreterTest {
    @Test
    @DisplayName("Compiler running HelloWorld program should write `Hello World!` to the output stream")
    void runHelloWorld() throws IOException, BrainfuckExecutionException, BrainfuckCompilationException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ExecutionRuntime runtime = new ExecutionRuntime(outputStream);

        BrainfuckJavaInterpreter compiler = new BrainfuckJavaInterpreter(runtime);
        String helloWorldProgram =
                "++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.<-.<.+++.------.--------" +
                        ".>>+.";
        compiler.run(new StringReader(helloWorldProgram));

        assertEquals("Hello World!", outputStream.toString());
    }

    @Test
    @DisplayName("Compiler running SumTwoValues program must sum up values in the execution runtime")
    void runSumTwoValues() throws IOException, BrainfuckExecutionException, BrainfuckCompilationException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ExecutionRuntime runtime = new ExecutionRuntime(outputStream);
        runtime.incrementCellValue((byte) 111);
        runtime.shiftPointer(1);
        runtime.incrementCellValue((byte) 11);
        runtime.shiftPointer(-1);

        BrainfuckJavaInterpreter compiler = new BrainfuckJavaInterpreter(runtime);
        String sumTwoValuesProgram = "[->+<]";
        compiler.run(new StringReader(sumTwoValuesProgram));

        assertEquals(0, runtime.getCellValue());
        runtime.shiftPointer(1);
        assertEquals(122, runtime.getCellValue());
    }

    @Test
    @DisplayName("Compiler running factorial algorithm should write first five factorials to the output stream")
    void runFactorialAlgorithm() throws IOException, BrainfuckExecutionException, BrainfuckCompilationException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ExecutionRuntime runtime = new ExecutionRuntime(outputStream);

        BrainfuckJavaInterpreter compiler = new BrainfuckJavaInterpreter(runtime);
        String factorialProgram =
                "+++++++++++++++++++++++++++++++++>+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++>+++" +
                        "+++++++>++++++>>+<<[>++++++++++++++++++++++++++++++++++++++++++++++++.---------------------" +
                        "---------------------------<<<<.-.>.<.+>>>>>>>++++++++++<<[->+>-[>+>>]>[+[-<+>]>+>>]<<<<<<]>" +
                        "[<+>-]>[-]>>>++++++++++<[->-[>+>>]>[+[-<+>]>+>>]<<<<<]>[-]>>[+++++++++++++++++++++++++++++++" +
                        "+++++++++++++++++.[-]]<[++++++++++++++++++++++++++++++++++++++++++++++++.[-]]<<<++++++++++++" +
                        "++++++++++++++++++++++++++++++++++++.[-]<<<<<<.>>+>[>>+<<-]>>[<<<[>+>+<<-]>>[<<+>>-]>-]<<<<-]";
        compiler.run(new StringReader(factorialProgram));

        assertEquals("0! = 1\n1! = 1\n2! = 2\n3! = 6\n4! = 24\n5! = 120\n", outputStream.toString());
    }
}
