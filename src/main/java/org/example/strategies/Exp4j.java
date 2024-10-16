package org.example.strategies;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.example.testers.MemoryTest;

public class Exp4j implements IParser{
    @Override
    public Object evaluate(String expression) {
        //System.out.println("before "+MemoryTest.getUsedMemory());
        Expression e = new ExpressionBuilder(expression).build();
        //System.out.println("after "+MemoryTest.getUsedMemory());
        return e.evaluate();
    }

    @Override
    public void addSource(Object source) {

    }
}
