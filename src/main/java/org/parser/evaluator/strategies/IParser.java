package org.parser.evaluator.strategies;

import java.util.HashMap;
import java.util.Map;

/**
 * The IParser interface provides a structure for evaluating mathematical expressions.
 * It includes methods for handling variables, custom functions, and basic evaluation logic.
 */
public interface IParser {

    /**
     * A shared map to store variables for expression evaluation.
     * The variable name is the key, and its value is a Double.
     */
    Map<String, Double> variables = new HashMap<>();

    /**
     * Evaluates an expression using the variables defined in the variables map.
     *
     * @param expression the mathematical expression to evaluate.
     * @return the result of the evaluation as an Object.
     */
    Object evaluate(String expression);

    /**
     * Evaluates an expression without using variables.
     *
     * @param expression the mathematical expression to evaluate.
     * @return the result of the evaluation as an Object.
     */
    Object evaluateWithoutVariables(String expression);

    /**
     * Evaluates an expression with support for custom functions and operators.
     *
     * @param expression the mathematical expression to evaluate.
     * @return the result of the evaluation as an Object.
     */
    Object evaluateWithCustomFunc(String expression);

    /**
     * Adds or updates a variable in the variables map for use in expression evaluation.
     *
     * @param name     the name of the variable.
     * @param variable the value of the variable as a Double.
     */
    default void setVariable(String name, Double variable) {
        variables.put(name, variable);
    }

    /**
     * Returns the name of the parser implementation as a String.
     *
     * @return the name of the parser.
     */
    String toString();
}
