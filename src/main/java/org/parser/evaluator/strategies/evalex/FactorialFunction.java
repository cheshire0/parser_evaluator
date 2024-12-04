package org.parser.evaluator.strategies.evalex;

import com.ezylang.evalex.Expression;
import com.ezylang.evalex.data.EvaluationValue;
import com.ezylang.evalex.functions.AbstractFunction;
import com.ezylang.evalex.functions.FunctionParameter;
import com.ezylang.evalex.parser.Token;

import static org.parser.evaluator.util.test.MathTestUtil.factorial;

@FunctionParameter(name = "value")
public class FactorialFunction extends AbstractFunction {
    @Override
    public EvaluationValue evaluate(
            Expression expression, Token functionToken, EvaluationValue... parameterValues) {

        EvaluationValue value = parameterValues[0];

        double result = factorial(value.getNumberValue().intValue());

        return EvaluationValue.of(
                result, expression.getConfiguration());
    }
}
