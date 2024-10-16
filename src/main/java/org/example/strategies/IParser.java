package org.example.strategies;

import org.example.testers.MemoryTest;

public interface IParser {
    Object evaluate(String expression);

    default Object evaluateWithGC(String expression){
        MemoryTest.runGarbageCollector();
        return evaluate(expression);
    }

    void addSource(Object source);
}
