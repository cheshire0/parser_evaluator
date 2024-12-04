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

    private boolean USE_RUNTIME = true;

    private Map<String, Integer> expressions = new HashMap<>();
    {
        expressions.put("3 * (4 + 5)", 2);
        expressions.put("sin(45) + cos(30)", 1);
        expressions.put("sqrt(16) * log(10) / 2", 2);
        expressions.put("10 ^ 3 + 5 * (7 - 3)", 4);
        expressions.put("((3 + 5) * 2) / (7 - 3)", 4);
    }

    public MemoryTest() {
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
    }

    private double testMemory(IParser parser, Map.Entry<String, Integer> entry) {

        String expression = entry.getKey();
        int length = entry.getValue();

        // Force Garbage Collection before measuring memory usage
        runGarbageCollector();

        // Start memory monitoring in a separate thread
        MemorySampler memorySampler = new MemorySampler();
        Thread samplerThread = new Thread(memorySampler);
        samplerThread.start();

        // Run the parser
        try {
            runGarbageCollector();
            long beforeMemory = getUsedMemory();

            parser.evaluateWithoutVariables(expression);

            long afterMemory = getUsedMemory();
            OutputHandler.log(new LogContext(this, parser, "incremental memory usage (byte), expr size: "+length,afterMemory - beforeMemory));

        } catch (Exception e) {
            OutputHandler.log(new LogContext(this, parser, "incremental memory usage (byte), expr size: "+length,"testing failed with exception: " + e.getMessage()));

        }

        // Stop the memory sampler
        memorySampler.stopSampling();

        try {
            samplerThread.join(); // Wait for sampler to finish
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Calculate the memory used
        long maxMemoryUsed = memorySampler.getMaxMemoryUsed();
        OutputHandler.log(new LogContext(this, parser,"max memory used during parsing (bytes), expr size: "+length, maxMemoryUsed));

        return maxMemoryUsed;
    }

    public static void runGarbageCollector() {
        System.gc();
        try {
            Thread.sleep(100); // give GC some time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        long runt=runtime.totalMemory() - runtime.freeMemory();
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

        // Get heap memory usage
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        long mx = heapMemoryUsage.getUsed()+nonHeapMemoryUsage.getUsed();

        return USE_RUNTIME? runt : mx;
    }

    @Override
    public Object testParser(IParser parser) {
        List<Double> results = new ArrayList<>();
        for(Map.Entry<String, Integer> entry : expressions.entrySet()){
            results.add(testMemory(parser, entry));
        }
        double average=0;
        for(Double result : results){
            average += result;
        }
        return average/results.size();
    }

    @Override
    public String toString() {
        return "Memory Usage";
    }

    public boolean isUSE_RUNTIME() {
        return USE_RUNTIME;
    }

    public void setUSE_RUNTIME(boolean USE_RUNTIME) {
        this.USE_RUNTIME = USE_RUNTIME;
    }
}