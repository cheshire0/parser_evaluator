package org.parser.evaluator.strategies.evalex;

import com.ezylang.evalex.EvaluationException;
import com.ezylang.evalex.Expression;
import com.ezylang.evalex.config.ExpressionConfiguration;
import com.ezylang.evalex.data.EvaluationValue;
import com.ezylang.evalex.parser.ParseException;
import org.parser.evaluator.strategies.IParser;

import java.util.Map;

/**
 * The EvalEx class provides a wrapper around the EvalEx library
 * to evaluate mathematical expressions with or without variables
 * and allows adding custom functions and operators.
 */
public class EvalEx implements IParser {

    /**
     * Evaluates a mathematical expression that includes variables.
     *
     * @param expressionString the mathematical expression as a string.
     * @return the evaluation result as an Object.
     */
    @Override
    public Object evaluate(String expressionString) {
        Expression expression = new Expression(expressionString);

        EvaluationValue result;
        try {
            result = expression.withValues(variables).evaluate();
        } catch (EvaluationException | ParseException e) {
            throw new RuntimeException(e);
        }

        return result.getValue();
    }

    /**
     * Evaluates a mathematical expression without any variables.
     *
     * @param expressionString the mathematical expression as a string.
     * @return the evaluation result as an Object.
     */
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

    /**
     * Evaluates a mathematical expression with custom functions and operators.
     *
     * @param expression the mathematical expression as a string.
     * @return the evaluation result as an Object.
     */
    @Override
    public Object evaluateWithCustomFunc(String expression) {
        // Create a custom ExpressionConfiguration with additional functions and operators.
        ExpressionConfiguration configuration =
                ExpressionConfiguration.defaultConfiguration()
                        .withAdditionalFunctions(
                                Map.entry("factorial", new FactorialFunction())) // Adds custom factorial function.
                        .withAdditionalOperators(
                                Map.entry("!", new FactorialOperator()) // Adds custom factorial operator (!).
                        );

        // Create a new Expression object using the custom configuration.
        Expression expr = new Expression(expression, configuration);

        EvaluationValue result = null;
        try {
            result = expr.evaluate();
        } catch (EvaluationException | ParseException e) {
            throw new RuntimeException(e);
        }

        return result.getValue();
    }

    /**
     * Provides a string representation of the class.
     *
     * @return the name of the class as a String.
     */
    @Override
    public String toString() {
        return "EvalEx";
    }
}
