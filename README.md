# Brainfuck to JavaScript compiler

[![Actions Status](https://github.com/annikulin/brainfuck-compiler/workflows/Gradle%20Build/badge.svg)](https://github.com/annikulin/brainfuck-compiler/actions)

## Overview

Brainfuck compiler that can execute Brainfuck programs in Java and transpile them to JavaScript.

The following phases of the compiler are supported:
* Lexical Analysis. The text of the program is broken up into tokens;
* Parsing. A sequence of tokens is being analyzed, and a parse model is constructed showing syntactic relation between commands;
* Code Optimization. Subsequent increment, decrement, and shift commands are replaced by a single aggregate operation;
* Code Generation. JavaScript code is generated based on a build parse model;
* Code Execution. Brainfuck program is being interpreted in Java.

## Quickstart

Use `BrainfuckCompiler.java` command-line utility to try out how the transpiler works. 

In order to execute a classic "Hello World!", create a Brainfuck program that prints "Hello World!" message to the output stream: 
```
echo "++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.<-.<.+++.------.--------.>>+.>++." > /tmp/HelloWorld.b
```

Then, start the program with `./gradlew run` providing the input file with Brainfuck program and the output file to write transpiled JavaScript code. 
```
./gradlew run --args="-i /tmp/HelloWorld.b -o /tmp/HelloWorld.js"
```

First, `BrainfuckCompiler` will execute the Brainfuck program in Java, then transpile it to JavaScript, and evaluate generated script inside the [Nashorn](https://docs.oracle.com/javase/8/docs/technotes/guides/scripting/prog_guide/api.html) JS engine.  
![Hello World in Brainfuck](helloworld_example.png)
