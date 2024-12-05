package org.parser.evaluator.testers.performance;

import org.parser.evaluator.strategies.IParser;
import org.parser.evaluator.testers.Test;
import org.parser.evaluator.testers.generator.ExpressionGenerator;
import org.parser.evaluator.testers.generator.IExpressionGenerator;
import org.parser.evaluator.util.log.OutputHandler;
import org.parser.evaluator.util.log.report.LogContext;
import org.parser.evaluator.util.test.MemorySampler;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.*;

public class MemoryTest extends Test {

    // Flag to determine whether to use Runtime memory usage or MXBean memory usage
    private boolean USE_RUNTIME = true;

    // Map to hold test expressions and their corresponding sizes (number of operations)
    private Map<String, Integer> expressions = new HashMap<>();
    {
        expressions.put("3 * (4 + 5)", 2); // Simple arithmetic
        expressions.put("sin(45) + cos(30)", 1); // Trigonometric expression
        expressions.put("sqrt(16) * log(10) / 2", 2); // Expression with square root and log
        expressions.put("10 ^ 3 + 5 * (7 - 3)", 4); // Exponentiation and arithmetic
        expressions.put("((3 + 5) * 2) / (7 - 3)", 4); // Complex arithmetic
    }

    // Constructor for the test, generating dynamic expressions with different sizes
    public MemoryTest() {
        IExpressionGenerator generator = new ExpressionGenerator();

        // Adding generated expressions with various sizes to the map
        String expr = generator.generate(20);
        expressions.put(expr, 20);
        expr = generator.generate(1000);
        expressions.put(expr, 1000);

        // Re-assign expressions with more size variations
        expressions = new LinkedHashMap<>();
        expressions.put(generator.generate(5), 5);
        expressions.put(generator.generate(20), 20);
        expressions.put(generator.generate(100), 100);
        expressions.put(generator.generate(500), 500);
        expressions.put(generator.generate(2500), 2500);
    }

    // Method to test memory usage while parsing an expression
    private double testMemory(IParser parser, Map.Entry<String, Integer> entry) {

        String expression = entry.getKey();
        int length = entry.getValue();

        // Force Garbage Collection before measuring memory usage to ensure accurate readings
        runGarbageCollector();

        // Start memory sampling in a separate thread
        MemorySampler memorySampler = new MemorySampler();
        Thread samplerThread = new Thread(memorySampler);
        samplerThread.start();

        // Try parsing the expression and measure memory usage
        try {
            // Run garbage collection before measuring the memory usage
            runGarbageCollector();
            long beforeMemory = getUsedMemory();

            // Evaluate the expression using the parser
            parser.evaluateWithoutVariables(expression);

            long afterMemory = getUsedMemory();
            OutputHandler.log(new LogContext(this, parser, "incremental memory usage (byte), expr size: "+length, afterMemory - beforeMemory));

        } catch (Exception e) {
            // If an exception occurs during evaluation, log the failure
            OutputHandler.log(new LogContext(this, parser, "incremental memory usage (byte), expr size: "+length, "testing failed with exception: " + e.getMessage()));
        }

        // Stop the memory sampling thread
        memorySampler.stopSampling();

        // Wait for the memory sampling thread to finish
        try {
            samplerThread.join(); // Wait for sampler to finish
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Calculate and log the maximum memory used during the evaluation
        long maxMemoryUsed = memorySampler.getMaxMemoryUsed();
        OutputHandler.log(new LogContext(this, parser, "max memory used during parsing (bytes), expr size: "+length, maxMemoryUsed));

        return maxMemoryUsed;
    }

    // Method to run garbage collection manually to clear unused memory
    public static void runGarbageCollector() {
        System.gc(); // Request garbage collection
        try {
            Thread.sleep(100); // Give GC some time to run
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Method to retrieve the current used memory
    public long getUsedMemory() {
        // Use Runtime to get the memory usage by the JVM
        Runtime runtime = Runtime.getRuntime();
        long runt = runtime.totalMemory() - runtime.freeMemory();

        // Get memory usage using MemoryMXBean (for heap and non-heap memory)
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        long mx = heapMemoryUsage.getUsed() + nonHeapMemoryUsage.getUsed();

        // Return either Runtime memory or MXBean memory based on the USE_RUNTIME flag
        return USE_RUNTIME ? runt : mx;
    }

    // Method to test the parser with various expressions and evaluate memory usage
    @Override
    public Object testParser(IParser parser) {
        List<Double> results = new ArrayList<>();

        // Iterate over all expressions and measure memory usage
        for(Map.Entry<String, Integer> entry : expressions.entrySet()){
            results.add(testMemory(parser, entry));
        }

        // Calculate the average memory usage
        double average = 0;
        for(Double result : results){
            average += result;
        }

        return average / results.size(); // Return the average memory usage across all tests
    }

    // Return the name of the test for reporting purposes
    @Override
    public String toString() {
        return "Memory Usage";
    }

    // Getter for USE_RUNTIME flag
    public boolean isUSE_RUNTIME() {
        return USE_RUNTIME;
    }

    // Setter for USE_RUNTIME flag
    public void setUSE_RUNTIME(boolean USE_RUNTIME) {
        this.USE_RUNTIME = USE_RUNTIME;
    }
}
