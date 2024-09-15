package org.example.strategies;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class Exp4j implements IParser{
    @Override
    public String evaluate(String expression) {
        Expression e = new ExpressionBuilder(expression).build();
        return String.valueOf(e.evaluate());
    }

    @Override
    public void addSource(Object source) {

    }
}
