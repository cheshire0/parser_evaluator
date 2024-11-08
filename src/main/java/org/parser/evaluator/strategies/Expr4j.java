package org.parser.evaluator.strategies;

import tk.pratanumandal.expr4j.ExpressionEvaluator;

public class Expr4j implements IParser{
    @Override
    public Object evaluate(String expression) {
        ExpressionEvaluator exprEval = new ExpressionEvaluator();
        //version 0.0.3 does not support variables afaik
        return exprEval.evaluate(expression);
    }

    @Override
    public String toString() {
        return "Expr4j";
    }
}
