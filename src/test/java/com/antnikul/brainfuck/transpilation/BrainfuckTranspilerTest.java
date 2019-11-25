package com.antnikul.brainfuck.transpilation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BrainfuckTranspilerTest {

    @Test
    @DisplayName("Transpiled HelloWorld program should write `Hello World!` to the output stream")
    void transpileHelloWorld() throws IOException, ScriptException {
        StringWriter output = new StringWriter();

        BrainfuckTranspiler transpiler = new BrainfuckTranspiler(new BrainfuckToJavaScriptExporter());
        String helloWorldProgram =
                "++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.<-.<.+++.------.--------" +
                        ".>>+.";
        transpiler.transpile(new StringReader(helloWorldProgram), output);
        assertEquals("Hello World!", evalJavaScript(output.toString()));
    }

    private String evalJavaScript(String script) throws ScriptException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        ScriptContext context = engine.getContext();
        StringWriter writer = new StringWriter();
        context.setWriter(writer);
        engine.eval(script);
        String programOutput = writer.toString();
        // removing line separators as Nashorn JS Engine adds them after every `print` statement
        return programOutput.replaceAll(System.lineSeparator(), "");
    }


}
