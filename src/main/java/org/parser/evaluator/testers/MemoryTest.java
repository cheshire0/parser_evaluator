package org.parser.evaluator.testers;

import org.parser.evaluator.strategies.IParser;
import org.parser.evaluator.testers.generator.ExpressionGenerator;
import org.parser.evaluator.testers.generator.IExpressionGenerator;

import java.util.ArrayList;
import java.util.List;

import static org.parser.evaluator.util.Logger.saveResultsToCSV;

public class MemoryTest extends Test{

    private final List<String> expressions;

//todo expression csomagot futtat többször
    public MemoryTest() {
        expressions = new ArrayList<>();
        IExpressionGenerator generator = new ExpressionGenerator();
        String expr= generator.generate(1000,1000);
        for(int i=0; i<1; i++){
            expressions.add(expr);
        }
    }

    private double testMemory(IParser parser, String expression) {

        // Force Garbage Collection before measuring memory usage
        runGarbageCollector();

        long beforeMemory = getUsedMemory();

        System.out.println("before memory "+ beforeMemory);

        // Run the parser
        parser.evaluateWithoutVariables(expression);

        //todo mintavételezve külön könyvtárral
        //TODO külön processben, lehet nem foglal elég helyet neki -> memória kérdezgetése tőle, nagyon gyakran, ns-en belül
        //runGarbageCollector();

        long afterMemory = getUsedMemory();

        System.out.println("after memory "+ afterMemory);

        // Calculate the memory used by the parser
        long memoryUsed = afterMemory - beforeMemory;

        System.out.println("Memory used by the parser: " + memoryUsed + " bytes");

        return memoryUsed;
    }

    public static void runGarbageCollector() {
        System.gc();
        try {
            Thread.sleep(100); // give GC some time
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    @Override
    protected Object testParser(IParser parser) {
        List<Double> results = new ArrayList<>();
        for(String expressionStr : expressions){
            results.add(testMemory(parser, expressionStr));
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
        return "Memory Usage";
    }
}