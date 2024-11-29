package org.parser.evaluator.strategies;

import org.parser.evaluator.testers.MemoryTest;

import java.util.HashMap;
import java.util.Map;

public interface IParser {

    Map<String, Double> variables = new HashMap<>();

    Object evaluate(String expression);

    Object evaluateWithoutVariables(String expression);

    Object evaluateWithCustomFunc(String expression);

    default Object evaluateWithGC(String expression){
        MemoryTest.runGarbageCollector();
        return evaluateWithoutVariables(expression);
    }

    default void setVariable(String name, Double variable){
        variables.put(name, variable);
    }

    String toString();
}
