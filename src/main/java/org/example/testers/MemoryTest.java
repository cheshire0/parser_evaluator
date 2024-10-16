package org.example.testers;

import org.example.strategies.IParser;
import org.example.testers.generator.ExpressionGenerator;
import org.example.testers.generator.IExpressionGenerator;

import java.util.ArrayList;

public class MemoryTest extends Test{

    private IExpressionGenerator generator = new ExpressionGenerator();
//todo expression csomagot futtat többször
    public MemoryTest(ArrayList<IParser> parsers) {
        super(parsers);
        expressions = new ArrayList<String>();
        String expr=generator.generate(1000,1000);
        for(int i=0; i<5; i++){
            expressions.add(expr);
        }
    }

    @Override
    protected void testParser(IParser parser, String expression) {

        // Force Garbage Collection before measuring memory usage
        runGarbageCollector();

        long beforeMemory = getUsedMemory();

        System.out.println("before memory "+ beforeMemory);

        // Run the parser
        runParser(expression, parser);

        //TODO külön processben, lehet nem foglal elég helyet neki -> memória kérdezgetése tőle, nagyon gyakran, ns-en belül
        //runGarbageCollector();

        long afterMemory = getUsedMemory();

        System.out.println("after memory "+ afterMemory);

        // Calculate the memory used by the parser
        long memoryUsed = afterMemory - beforeMemory;

        System.out.println("Memory used by the parser: " + memoryUsed + " bytes");
    }

    private void runParser(String expressionStr, IParser parser) {
        // Simulate parsing the expression multiple times
        for (int i = 0; i < 100; i++) {
            Object result = parser.evaluateWithGC(expressionStr);
            //TODO expression váltogatása (nagy tömb, 10 000 elem, kicsit különböznek), parser újra inicializálva
        }
    }

    public static void runGarbageCollector() {
        Runtime runtime = Runtime.getRuntime();
        for (int i = 0; i < 5; i++) {
            runtime.gc(); // Request garbage collection
        }
    }

    public static long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}