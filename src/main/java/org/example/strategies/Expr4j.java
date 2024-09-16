package org.example.strategies;

import tk.pratanumandal.expr4j.ExpressionEvaluator;

public class Expr4j implements IParser{
    @Override
    public String evaluate(String expression) {
        ExpressionEvaluator exprEval = new ExpressionEvaluator();
        return String.valueOf(exprEval.evaluate(expression));
    }

    @Override
    public void addSource(Object source) {

    }
}
