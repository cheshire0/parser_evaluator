package org.example.testers;

import org.example.strategies.IParser;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class SpeedTest {

    private final ArrayList<IParser> parsers;

    public SpeedTest(ArrayList<IParser> parsers) {
        this.parsers = parsers;
    }

    private String[] expressions = {
            "3 * (4 + 5)",
            "sin(45) + cos(30)",
            "sqrt(16) * log(10) / 2",
            "10 ^ 3 + 5 * (7 - 3)",
            "((3 + 5) * 2) / (7 - 3)"
    };

    private int iterations = 10000;

    public void test() {
        for (IParser parser : parsers) {
            System.out.println("Testing parser: " + parser.getClass().getName());
            for (String expressionStr : expressions) {
                System.out.println("Testing expression: " + expressionStr);
                String error = null;
                double result = 0.0;
                try {
                    result = Double.parseDouble(parser.evaluate(expressionStr));
                } catch (Exception e) {
                    error = e.getMessage();
                }
                System.out.println("Result: " + (error == null ? result : error));

                warmUp(expressionStr, parser);

                // Measure the time for multiple iterations
                long totalTime = 0;
                for (int i = 0; i < iterations; i++) {
                    long startTime = System.nanoTime();

                    // Parse and evaluate the expression
                    try {
                        result = Double.parseDouble(parser.evaluate(expressionStr));
                    } catch (Exception ignored) {
                        //TODO if not ran properly, shouldn't "compete"
                    }

                    long endTime = System.nanoTime();
                    totalTime += (endTime - startTime);
                }

                // Calculate average time per iteration
                double averageTimeNs = totalTime / (double) iterations;
                double averageTimeMs = TimeUnit.NANOSECONDS.toMillis((long) averageTimeNs);

                System.out.println("Average time: " + averageTimeNs + " ns (" + averageTimeMs + " ms)");
            }
            System.out.println();
        }
    }

    // Warm-up method to trigger JVM optimizations
    private static void warmUp(String expressionStr, IParser parser) {
        for (int i = 0; i < 1000; i++) {
            try {
                parser.evaluate(expressionStr);
            } catch (Exception ignored) {
            }
        }
    }
}