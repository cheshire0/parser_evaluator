package org.parser.evaluator.testers;

import org.parser.evaluator.strategies.IParser;
import org.parser.evaluator.testers.generator.ExpressionGenerator;
import org.parser.evaluator.testers.generator.IExpressionGenerator;
import org.parser.evaluator.util.MemorySampler;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.parser.evaluator.util.Logger.saveResultsToCSV;

public class MemoryTest extends Test{

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
    }

    private double testMemory(IParser parser, Map.Entry<String, Integer> entry) {
        String expression = entry.getKey();
        int length = entry.getValue();
        String testName = this+" for expr: "+expression;
        if(length > 10) testName = this+" for expr of size: "+length;

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
            System.out.println("before memory "+ beforeMemory);

            parser.evaluateWithoutVariables(expression);

            long afterMemory = getUsedMemory();
            System.out.println("after memory "+ afterMemory);

            saveResultsToCSV(testName+", incremental memory usage, (byte)", parser, afterMemory-beforeMemory);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            saveResultsToCSV(testName, parser, "testing failed with exception: "+e.getMessage());
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

        saveResultsToCSV(testName+", max heap usage sampled, (byte)", parser, maxMemoryUsed);

        System.out.println("Max memory used during parsing: " + maxMemoryUsed + " bytes");

        return maxMemoryUsed;
    }


//    private double testMemory(IParser parser, String expression) {
//
//        // Force Garbage Collection before measuring memory usage
//        runGarbageCollector();
//
//        long beforeMemory = getUsedMemory();
//
//        System.out.println("before memory "+ beforeMemory);
//
//        // Run the parser
//        parser.evaluateWithoutVariables(expression);
//
//        //todo mintavételezve külön könyvtárral
//        //TODO külön processben, lehet nem foglal elég helyet neki -> memória kérdezgetése tőle, nagyon gyakran, ns-en belül
//        //runGarbageCollector();
//
//        long afterMemory = getUsedMemory();
//
//        System.out.println("after memory "+ afterMemory);
//
//        // Calculate the memory used by the parser
//        long memoryUsed = afterMemory - beforeMemory;
//
//        System.out.println("Memory used by the parser: " + memoryUsed + " bytes");
//
//        return memoryUsed;
//    }

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
        System.out.println("RUNTIME");
        long runt=runtime.totalMemory() - runtime.freeMemory();
        System.out.println(runt);
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();

        // Get heap memory usage
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryMXBean.getNonHeapMemoryUsage();
        System.out.println("MX");
        long mx = heapMemoryUsage.getUsed()+nonHeapMemoryUsage.getUsed();
        System.out.println(mx);

        return USE_RUNTIME? runt : mx;
    }

    @Override
    protected Object testParser(IParser parser) {
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