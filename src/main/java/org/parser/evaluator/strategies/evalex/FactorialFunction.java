package org.parser.evaluator.strategies.evalex;

import com.ezylang.evalex.Expression;
import com.ezylang.evalex.data.EvaluationValue;
import com.ezylang.evalex.functions.AbstractFunction;
import com.ezylang.evalex.functions.FunctionParameter;
import com.ezylang.evalex.parser.Token;

import static org.parser.evaluator.util.test.MathTestUtil.factorial;

/**
 * FactorialFunction is a custom function implementation for EvalEx
 * that calculates the factorial of a given number.
 */
@FunctionParameter(name = "value") // Annotates the expected parameter for the function.
public class FactorialFunction extends AbstractFunction {

    /**
     * Evaluates the factorial of the provided parameter.
     *
     * @param expression     The current expression being evaluated.
     * @param functionToken  The token representing this function in the expression.
     * @param parameterValues The parameter values passed to the function during evaluation.
     *                        This function expects a single numeric parameter.
     * @return An EvaluationValue containing the computed factorial result.
     */
    @Override
    public EvaluationValue evaluate(
            Expression expression, Token functionToken, EvaluationValue... parameterValues) {

        // Retrieve the first parameter (value) from the parameter values.
        EvaluationValue value = parameterValues[0];

        // Calculate the factorial using an external utility method.
        double result = factorial(value.getNumberValue().intValue());

        // Wrap the result in an EvaluationValue and return it.
        return EvaluationValue.of(result, expression.getConfiguration());
    }
}
