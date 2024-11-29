package org.parser.evaluator.strategies.evalex;

import com.ezylang.evalex.EvaluationException;
import com.ezylang.evalex.Expression;
import com.ezylang.evalex.config.ExpressionConfiguration;
import com.ezylang.evalex.data.EvaluationValue;
import com.ezylang.evalex.parser.ParseException;
import org.parser.evaluator.strategies.IParser;

import java.util.Map;

public class EvalEx implements IParser {

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
    public Object evaluateWithCustomFunc(String expression) {
        ExpressionConfiguration configuration =
                ExpressionConfiguration.defaultConfiguration()
                        .withAdditionalFunctions(
                                Map.entry("factorial", new FactorialFunction()));
        Expression expr = new Expression(expression, configuration);
        EvaluationValue result = null;
        try {
            result = expr.evaluate();
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
