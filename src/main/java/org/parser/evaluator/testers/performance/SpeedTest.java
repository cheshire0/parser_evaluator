package org.parser.evaluator.testers.performance;

import org.parser.evaluator.strategies.IParser;
import org.parser.evaluator.testers.Test;
import org.parser.evaluator.testers.generator.ExpressionGenerator;
import org.parser.evaluator.testers.generator.IExpressionGenerator;
import org.parser.evaluator.util.log.OutputHandler;
import org.parser.evaluator.util.log.report.LogContext;

import java.util.*;

public class SpeedTest extends Test {

    // Number of iterations to run each test for average speed calculation
    private final int iterations = 100;

    // Map of expressions and their sizes (complexity level)
    private Map<String, Integer> expressions = new HashMap<>();
    {
        expressions.put("3 * (4 + 5)", 2); // Simple arithmetic expression
        expressions.put("sin(45) + cos(30)", 1); // Trigonometric expression
        expressions.put("sqrt(16) * log(10) / 2", 2); // Square root and logarithmic operations
        expressions.put("10 ^ 3 + 5 * (7 - 3)", 4); // Arithmetic with exponentiation
        expressions.put("((3 + 5) * 2) / (7 - 3)", 4); // Nested arithmetic operations
        expressions.put("((3 + (2 * (5 - (7 / (3 + 2))))) * (sin(45) + log(100))) / (sqrt((2^3) + (log(50) - tan(30))) - 4)", 11); // Complex nested expression
    }

    // Constructor for the test, adding dynamically generated expressions with varying complexity
    public SpeedTest() {
        IExpressionGenerator generator = new ExpressionGenerator();

        // Generating expressions with different complexities and adding them to the map
        String expr = generator.generate(20);
        expressions.put(expr, 20);
        expr = generator.generate(1000);
        expressions.put(expr, 1000);

        // Re-assigning expressions with more size variations
        expressions = new LinkedHashMap<>();
        expressions.put(generator.generate(5), 5);
        expressions.put(generator.generate(20), 20);
        expressions.put(generator.generate(100), 100);
        expressions.put(generator.generate(500), 500);
        expressions.put(generator.generate(2500), 2500);

        // Logging the number of iterations to be performed in the speed test
        OutputHandler.log("Speed Test - iteration number: " + iterations);
    }

    // The main method to test the parser's speed on various expressions
    @Override
    public Object testParser(IParser parser){
        List<Double> results = new ArrayList<>();

        // Iterate over all expressions and measure speed
        for(Map.Entry<String, Integer> entry : expressions.entrySet()){
            String expr = entry.getKey();
            int length = entry.getValue();
            double speed = testSpeed(parser, expr, length);

            // If the test was successful, add the speed result, otherwise add infinity to indicate failure
            if(speed > 0){
                results.add(speed);
            } else {
                results.add(Double.POSITIVE_INFINITY);
            }
        }

        // Calculate the average speed across all expressions
        double average = 0;
        for(Double result : results){
            average += result;
        }

        // Log the average time for parsing all expressions
        OutputHandler.log(new LogContext(this, parser, "average for all tested expr", average / results.size()));

        return average / results.size(); // Return the average time per test
    }

    // Return the name of the test for reporting purposes
    @Override
    public String toString() {
        return "Speed";
    }

    // Method to measure the speed of parsing an expression (time per iteration)
    private double testSpeed(IParser parser, String expressionStr, int length) {

        // Warm-up the parser to ensure JVM optimizations kick in
        warmUp(expressionStr, parser);

        // Measure the time for multiple iterations (to get stable results)
        long totalTime = 0;
        for (int i = 0; i < iterations; i++) {
            long startTime = System.nanoTime(); // Start the timer

            // Try parsing and evaluating the expression
            try {
                parser.evaluateWithoutVariables(expressionStr);
            } catch (Exception e) {
                // If an exception occurs, log the error and return a negative value to indicate failure
                OutputHandler.log(new LogContext(this, parser, "expression of size " + length, "error: "+e.getMessage()));
                return -1;
            }

            long endTime = System.nanoTime(); // End the timer
            totalTime += (endTime - startTime); // Add the elapsed time to total
        }

        // Calculate the average time per iteration in nanoseconds
        double averageTimeNs = totalTime / (double) iterations;

        // Log the average time for the current expression
        OutputHandler.log(new LogContext(this, parser, "average time (ns) for expression of size: " + length, averageTimeNs));

        return averageTimeNs; // Return the average time per iteration
    }

    // Warm-up method to trigger JVM optimizations, executed before actual tests
    private static void warmUp(String expressionStr, IParser parser) {
        // Run the expression 10 times to ensure the JVM has had a chance to optimize the parser
        for (int i = 0; i < 10; i++) {
            try {
                parser.evaluateWithoutVariables(expressionStr);
            } catch (Exception ignored) {
            }
        }
    }
}
