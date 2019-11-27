package com.antnikul.brainfuck;

import com.antnikul.brainfuck.compilation.BrainfuckCompilationException;
import com.antnikul.brainfuck.compilation.BrainfuckTranspiler;
import com.antnikul.brainfuck.compilation.codegeneration.JavaScriptCodeGenerator;
import com.antnikul.brainfuck.execution.BrainfuckExecutionException;
import com.antnikul.brainfuck.execution.BrainfuckJavaInterpreter;
import com.antnikul.brainfuck.execution.ExecutionRuntime;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * A command line program that takes Brainfuck program as input and executes it in Java. In addition, if output
 * parameter is given, the program is transpiled to JavaScript and executed in Nashorn JS engine.
 */
@SuppressWarnings("PMD.DoNotCallSystemExit")
public class BrainfuckCompiler {

    private static final Logger LOGGER = LogManager.getLogger(BrainfuckCompiler.class);

    private static final String INPUT_ARG_NAME = "input";
    private static final String OUTPUT_ARG_NAME = "output";

    // avoid instantiating utility class
    private BrainfuckCompiler() {
    }

    public static void main(String[] args) throws IOException {
        CommandLine cmd = parseCommandLineArgs(args);

        String inputFilePath = cmd.getOptionValue(INPUT_ARG_NAME);
        String outputFilePath = cmd.getOptionValue(OUTPUT_ARG_NAME);

        executeBrainfuckProgram(inputFilePath);
        if (Strings.isNotBlank(outputFilePath)) {
            transpileBrainfuckProgram(inputFilePath, outputFilePath);
            executeJavaScriptProgram(outputFilePath);
        }
    }

    private static CommandLine parseCommandLineArgs(String[] args) {
        Options options = new Options();
        options.addRequiredOption("i", INPUT_ARG_NAME, true, "Input file with Brainfuck program");
        options.addOption("o", OUTPUT_ARG_NAME, true, "Output file for transpiled JavaScript program");

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            formatter.printHelp("./gradlew run --args=\"\"", options);
            System.exit(1);
        }
        return cmd;
    }

    private static void executeBrainfuckProgram(String inputFilePath) throws IOException {
        try (BufferedReader input = Files.newBufferedReader(Paths.get(inputFilePath), Charset.defaultCharset())) {
            LOGGER.info("Executing Brainfuck program in Java \u231B");
            ExecutionRuntime runtime = new ExecutionRuntime(System.out);
            new BrainfuckJavaInterpreter(runtime).run(input);
            LOGGER.info("Brainfuck program has successfully run in Java \uD83D\uDC4D");
        } catch (BrainfuckExecutionException e) {
            LOGGER.error("Failed to execute Brainfuck program \u274C", e);
            System.exit(1);
        } catch (BrainfuckCompilationException e) {
            LOGGER.error("Failed to compile Brainfuck program \u274C", e);
            System.exit(1);
        }
    }

    private static void transpileBrainfuckProgram(String inputFilePath, String outputFilePath) throws IOException {
        File outputFile = new File(outputFilePath);
        outputFile.createNewFile();

        try (
                BufferedReader input = Files.newBufferedReader(Paths.get(inputFilePath), Charset.defaultCharset());
                BufferedWriter output = Files.newBufferedWriter(Paths.get(outputFilePath), Charset.defaultCharset())
        ) {
            LOGGER.info("Transpiling Brainfuck program to JavaScript \u231B");
            BrainfuckTranspiler transpiler = new BrainfuckTranspiler(new JavaScriptCodeGenerator());
            transpiler.transpile(input, output);
            LOGGER.info("Brainfuck program has been successfully transpiled to JavaScript ({}) \uD83D\uDC4D",
                    outputFilePath);
        } catch (BrainfuckCompilationException e) {
            LOGGER.error("Failed to transpile Brainfuck program \u274C", e);
            System.exit(1);
        }
    }

    private static void executeJavaScriptProgram(String scriptFilePath) throws IOException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        try (BufferedReader input = Files.newBufferedReader(Paths.get(scriptFilePath), Charset.defaultCharset())) {
            LOGGER.info("Executing transpiled Brainfuck program in JavaScript \u231B");
            engine.eval(input);
            LOGGER.info("Transpiled Brainfuck program has successfully run in JavaScript \uD83D\uDC4D");
        } catch (ScriptException e) {
            LOGGER.error("Failed to execute transpiled JavaScript program \u274C", e);
            System.exit(1);
        }
    }
}
