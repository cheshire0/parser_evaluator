package org.parser.evaluator.strategies;

import com.expression.parser.Parser;

/**
 * The JavaMathExpressionParser class implements the IParser interface for evaluating
 * mathematical expressions using the com.expression.parser library.
 * It supports evaluating expressions with and without variables.
 */
public class JavaMathExpressionParser implements IParser {

    /**
     * Evaluates an expression using the variables defined in the variables map.
     *
     * @param expression the mathematical expression to evaluate.
     * @return the result of the evaluation as an Object (typically a Double).
     */
    @Override
    public Object evaluate(String expression) {
        // Use the Parser to evaluate the expression with variables
        return Parser.eval(
                expression,
                variables.keySet().toArray(new String[0]), // Convert variable names to an array
                variables.values().toArray(new Double[0]) // Convert variable values to an array
        );
    }

    /**
     * Evaluates an expression without using variables.
     *
     * @param expression the mathematical expression to evaluate.
     * @return the result of the evaluation as an Object (typically a Double).
     */
    @Override
    public Object evaluateWithoutVariables(String expression) {
        // Use the Parser to evaluate the expression without variables
        return Parser.simpleEval(expression);
    }

    /**
     * Evaluates an expression with custom functions. (Not implemented in this parser.)
     *
     * @param expression the mathematical expression to evaluate.
     * @return currently returns null as custom function support cannot be implemented.
     */
    @Override
    public Object evaluateWithCustomFunc(String expression) {
        // Placeholder for custom function evaluation
        return null;
    }

    /**
     * Returns the name of this parser implementation as a String.
     *
     * @return the name of the parser ("JavaMathExpressionParser").
     */
    @Override
    public String toString() {
        return "JavaMathExpressionParser";
    }
}
