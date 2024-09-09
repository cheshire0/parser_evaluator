package org.example.strategies;

import com.ezylang.evalex.EvaluationException;
import com.ezylang.evalex.Expression;
import com.ezylang.evalex.data.EvaluationValue;
import com.ezylang.evalex.parser.ParseException;

public class EvalEx implements IParser{

    @Override
    public Object evaluate(String expressionString) {
        Expression expression = new Expression(expressionString);

        EvaluationValue result = null;
        try {
            result = expression.evaluate();
        } catch (EvaluationException | ParseException e) {
            throw new RuntimeException(e);
        }

        return result.getNumberValue();
    }

    @Override
    public void addSource(Object source) {

    }
}
