package org.example.testers;

import org.example.strategies.IParser;

import java.util.ArrayList;

public class MemoryTest {

    private final ArrayList<IParser> parsers;

    private String expressionStr = "3 * (4 + 5) + sqrt(16) / sin(10)";

    public MemoryTest(ArrayList<IParser> parsers) {
        this.parsers = parsers;
    }

    public void test(){
        for (IParser parser : parsers) {
            System.out.println("Testing parser: " + parser.getClass().getName());
            System.out.println("Expression: " + expressionStr);
            System.out.println("Result: " + parser.evaluate(expressionStr));

            testMemoryUse(parser);
            System.out.println();
        }
    }

    private void testMemoryUse(IParser parser) {

        // Force Garbage Collection before measuring memory usage
        runGarbageCollector();

        long beforeMemory = getUsedMemory();

        System.out.println("before memory "+ beforeMemory);

        // Run the parser
        runParser(expressionStr, parser);

        //runGarbageCollector();

        long afterMemory = getUsedMemory();

        System.out.println("after memory "+ afterMemory);

        // Calculate the memory used by the parser
        long memoryUsed = afterMemory - beforeMemory;

        System.out.println("Memory used by the parser: " + memoryUsed + " bytes");
    }

    private void runParser(String expressionStr, IParser parser) {
        // Simulate parsing the expression multiple times
        for (int i = 0; i < 10000; i++) {
            String result = parser.evaluate(expressionStr);
        }
    }

    private void runGarbageCollector() {
        Runtime runtime = Runtime.getRuntime();
        for (int i = 0; i < 5; i++) {
            runtime.gc(); // Request garbage collection
        }
    }

    private long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}