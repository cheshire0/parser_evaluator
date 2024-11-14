package org.parser.evaluator.strategies;

import com.ezylang.evalex.EvaluationException;
import com.ezylang.evalex.Expression;
import com.ezylang.evalex.data.EvaluationValue;
import com.ezylang.evalex.parser.ParseException;

import java.util.HashMap;
import java.util.Map;

public class EvalEx implements IParser{

    @Override
    public Object evaluate(String expressionString) {
        Expression expression = new Expression(expressionString);

        EvaluationValue result = null;
        try {
            result = expression.withValues(variables).evaluate();
        } catch (EvaluationException | ParseException e) {
            throw new RuntimeException(e);
        }

        return result.getValue();
    }

    @Override
    public Object evaluateWithoutVariables(String expressionString) {
        Expression expression = new Expression(expressionString);

        EvaluationValue result = null;
        try {
            result = expression.evaluate();
        } catch (EvaluationException | ParseException e) {
            throw new RuntimeException(e);
        }

        return result.getValue();
    }

    @Override
    public String toString() {
        return "EvalEx";
    }
}
