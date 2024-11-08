package org.parser.evaluator.strategies;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.HashMap;
import java.util.Map;

public class Exp4j implements IParser{

    @Override
    public Object evaluate(String expression) {
        //System.out.println("before "+MemoryTest.getUsedMemory());
        Expression e = new ExpressionBuilder(expression)
                .variables(variables.keySet())
                .build()
                .setVariables(variables);
        //System.out.println("after "+MemoryTest.getUsedMemory());
        return e.evaluate();
    }

    @Override
    public String toString() {
        return "Exp4j";
    }
}
