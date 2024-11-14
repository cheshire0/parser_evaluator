package org.parser.evaluator.strategies;

import com.expression.parser.Parser;
import com.expression.parser.util.Point;

public class JavaMathExpressionParser implements IParser{
    @Override
    public Object evaluate(String expression) {
        return Parser.eval(expression,
                variables.keySet().toArray(new String[0]),
                variables.values().toArray(new Double[0]));
    }

    @Override
    public Object evaluateWithoutVariables(String expression) {
        return Parser.simpleEval(expression);
    }

    @Override
    public String toString(){
        return "JavaMathExpressionParser";
    }
}
