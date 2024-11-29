package org.parser.evaluator.testers;

import org.parser.evaluator.strategies.IParser;
import org.parser.evaluator.testers.generator.ExpressionGenerator;
import org.parser.evaluator.testers.generator.IExpressionGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.parser.evaluator.util.Logger.saveResultsToCSV;

public class SpeedTest extends Test{

    private int iterations = 100;

    private Map<String, Integer> expressions = new HashMap<>();
    {
        expressions.put("3 * (4 + 5)", 2);
        expressions.put("sin(45) + cos(30)", 1);
        expressions.put("sqrt(16) * log(10) / 2", 2);
        expressions.put("10 ^ 3 + 5 * (7 - 3)", 4);
        expressions.put("((3 + 5) * 2) / (7 - 3)", 4);
        expressions.put("((3 + (2 * (5 - (7 / (3 + 2))))) * (sin(45) + log(100))) / (sqrt((2^3) + (log(50) - tan(30))) - 4)", 11);
    }

    public SpeedTest() {
        IExpressionGenerator generator = new ExpressionGenerator();
        String expr= generator.generate(20);
        expressions.put(expr,20);
        expr= generator.generate(1000);
        expressions.put(expr,1000);

        saveResultsToCSV("Speed Test - iteration number: "+iterations);
    }

    //TODO több különböző iterationre összahasonlítani

    @Override
    protected Object testParser(IParser parser){
        List<Double> results = new ArrayList<>();
        for(Map.Entry<String, Integer> entry : expressions.entrySet()){
            String expr = entry.getKey();
            int length = entry.getValue();
            double speed = testSpeed(parser, expr, length);
            if(speed > 0){
                results.add(speed);
            }
            //test did not run properly
            else results.add(Double.POSITIVE_INFINITY);
        }
        double average=0;
        for(Double result : results){
            average += result;
        }
        saveResultsToCSV(this+" average for all tested expr", parser,  average/ results.size());
        return average/results.size();
    }

    @Override
    public String toString() {
        return "Speed";
    }

    private double testSpeed(IParser parser, String expressionStr, int length) {
        System.out.println("Testing parser: " + parser.getClass().getName());

        warmUp(expressionStr, parser);

        // Measure the time for multiple iterations
        long totalTime = 0;
        for (int i = 0; i < iterations; i++) {
            long startTime = System.nanoTime();

            // Parse and evaluate the expression
            try {
                parser.evaluateWithoutVariables(expressionStr);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                saveResultsToCSV(this+" for expression of size: "+length, parser, "Error: " + e.getMessage());
                return -1;
            }

            long endTime = System.nanoTime();
            totalTime += (endTime - startTime);
        }

        // Calculate average time per iteration
        double averageTimeNs = totalTime / (double) iterations;
        double averageTimeMs = TimeUnit.NANOSECONDS.toMillis((long) averageTimeNs);

        String testName = this+" for expr: "+expressionStr;
        if(length > 10) testName = this+" for expr of size: "+length;
        saveResultsToCSV(testName+", (ns)", parser, averageTimeNs);

        System.out.println("Average time: " + averageTimeNs + " ns (" + averageTimeMs + " ms)"+" for expression of size: "+length);

        return averageTimeNs;
    }

    // Warm-up method to trigger JVM optimizations
    private static void warmUp(String expressionStr, IParser parser) {
        for (int i = 0; i < 10; i++) {
            try {
                parser.evaluateWithoutVariables(expressionStr);
            } catch (Exception ignored) {
            }
        }
    }
}