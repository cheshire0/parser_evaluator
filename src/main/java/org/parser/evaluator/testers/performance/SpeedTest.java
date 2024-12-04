package org.parser.evaluator.testers.performance;

import org.parser.evaluator.strategies.IParser;
import org.parser.evaluator.testers.Test;
import org.parser.evaluator.testers.generator.ExpressionGenerator;
import org.parser.evaluator.testers.generator.IExpressionGenerator;
import org.parser.evaluator.util.log.OutputHandler;
import org.parser.evaluator.util.log.report.LogContext;

import java.util.*;

public class SpeedTest extends Test {

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

        expressions = new LinkedHashMap<>();
        expressions.put(generator.generate(5),5);
        expressions.put(generator.generate(20),20);
        expressions.put(generator.generate(100),100);
        expressions.put(generator.generate(500),500);
        expressions.put(generator.generate(2500),2500);
        OutputHandler.log("Speed Test - iteration number: "+iterations);

    }

    @Override
    public Object testParser(IParser parser){
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
        OutputHandler.log(new LogContext(this, parser, "average for all tested expr", average/ results.size()));
        return average/results.size();
    }

    @Override
    public String toString() {
        return "Speed";
    }

    private double testSpeed(IParser parser, String expressionStr, int length) {

        warmUp(expressionStr, parser);

        // Measure the time for multiple iterations
        long totalTime = 0;
        for (int i = 0; i < iterations; i++) {
            long startTime = System.nanoTime();

            // Parse and evaluate the expression
            try {
                parser.evaluateWithoutVariables(expressionStr);
            } catch (Exception e) {
                OutputHandler.log(new LogContext(this, parser, "expression of size " + length, "error: "+e.getMessage()));
                return -1;
            }

            long endTime = System.nanoTime();
            totalTime += (endTime - startTime);
        }

        // Calculate average time per iteration
        double averageTimeNs = totalTime / (double) iterations;

        OutputHandler.log(new LogContext(this, parser, "average time (ns) for expression of size: " + length, averageTimeNs));

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