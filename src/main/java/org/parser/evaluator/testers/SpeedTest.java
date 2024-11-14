package org.parser.evaluator.testers;

import org.parser.evaluator.strategies.IParser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.parser.evaluator.util.Logger.saveResultsToCSV;

public class SpeedTest extends Test{

    private List<String> expressions = List.of(
            "3 * (4 + 5)",
            "sin(45) + cos(30)",
            "sqrt(16) * log(10) / 2",
            "10 ^ 3 + 5 * (7 - 3)",
            "((3 + 5) * 2) / (7 - 3)"
    );

    private int iterations = 10000;
    //TODO több különböző iterationre összahasonlítani

    @Override
    protected Object testParser(IParser parser){
        List<Double> results = new ArrayList<>();
        for(String expressionStr : expressions){
            results.add(testSpeed(parser, expressionStr));
        }
        double average=0;
        for(Double result : results){
            average += result;
        }
        saveResultsToCSV(this, parser, average/ results.size());
        return average/results.size();
    }

    @Override
    public String toString() {
        return "Speed";
    }

    private double testSpeed(IParser parser, String expressionStr) {
        System.out.println("Testing parser: " + parser.getClass().getName());
        System.out.println("Testing expression: " + expressionStr);
        String error = null;
        double result = 0.0;
        try {
            result = Double.parseDouble(parser.evaluateWithoutVariables(expressionStr).toString());
        } catch (Exception e) {
            error = e.getMessage();
            saveResultsToCSV(this, parser, "Exception - " + error);
        }
        System.out.println("Result: " + (error == null ? result : error));

        warmUp(expressionStr, parser);

        // Measure the time for multiple iterations
        long totalTime = 0;
        for (int i = 0; i < iterations; i++) {
            long startTime = System.nanoTime();

            // Parse and evaluate the expression
            try {
                result = Double.parseDouble((String)parser.evaluate(expressionStr));
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

        return averageTimeNs;
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