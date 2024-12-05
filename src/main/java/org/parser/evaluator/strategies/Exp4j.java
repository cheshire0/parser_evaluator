package org.parser.evaluator.strategies;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;

import static org.parser.evaluator.util.test.MathTestUtil.factorial;

/**
 * The Exp4j class is an implementation of the IParser interface
 * that uses the Exp4j library to evaluate mathematical expressions.
 * It supports variable substitution and adding custom functions and operators.
 */
public class Exp4j implements IParser {

    /**
     * Evaluates a mathematical expression with variables.
     *
     * @param expression the mathematical expression as a string.
     * @return the evaluation result as an Object.
     */
    @Override
    public Object evaluate(String expression) {
        // Build the expression with variables and assign their values.
        Expression e = new ExpressionBuilder(expression)
                .variables(variables.keySet()) // Define variables available in the expression.
                .build()
                .setVariables(variables); // Set the values of the variables.

        // Evaluate the expression and return the result.
        return e.evaluate();
    }

    /**
     * Evaluates a mathematical expression without variables.
     *
     * @param expression the mathematical expression as a string.
     * @return the evaluation result as an Object.
     */
    @Override
    public Object evaluateWithoutVariables(String expression) {
        // Build the expression without any variables.
        Expression e = new ExpressionBuilder(expression).build();

        // Evaluate the expression and return the result.
        return e.evaluate();
    }

    /**
     * Evaluates a mathematical expression with custom functions and operators.
     *
     * @param expression the mathematical expression as a string.
     * @return the evaluation result as an Object.
     */
    @Override
    public Object evaluateWithCustomFunc(String expression) {
        // Define a custom "factorial" function.
        Function fact = new Function("factorial", 1) {
            @Override
            public double apply(double... args) {
                // Calculate factorial of the first argument (cast to int).
                return factorial((int) args[0]);
            }
        };

        // Define a custom "!" operator for factorial.
        Operator operator = new Operator("!", 1, true, 10001) {
            @Override
            public double apply(double... args) {
                // Calculate factorial of the first argument (cast to int).
                return factorial((int) args[0]);
            }
        };

        // Build and evaluate the expression using the custom function and operator.
        return new ExpressionBuilder(expression)
                .function(fact) // Add the custom "factorial" function.
                .operator(operator) // Add the custom "!" operator.
                .build()
                .evaluate();
    }

    /**
     * Returns the string representation of the class.
     *
     * @return the name of the class as a String.
     */
    @Override
    public String toString() {
        return "Exp4j";
    }
}
