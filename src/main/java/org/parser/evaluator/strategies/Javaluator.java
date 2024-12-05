package org.parser.evaluator.strategies;

import com.fathzer.soft.javaluator.*;

import java.util.Iterator;

import static org.parser.evaluator.util.test.MathTestUtil.factorial;

/**
 * The Javaluator class implements the IParser interface for evaluating mathematical expressions
 * using the Javaluator library. It provides support for variables, custom functions, and custom operators.
 */
public class Javaluator implements IParser {

    // Default evaluator instance for evaluating expressions
    private final DoubleEvaluator eval = new DoubleEvaluator();

    // Variable set for storing variables and their values
    private final StaticVariableSet<Double> variables = new StaticVariableSet<>();

    /**
     * Evaluates an expression using the variables defined in the variables map.
     *
     * @param expression the mathematical expression to evaluate.
     * @return the result of the evaluation as a Double.
     */
    @Override
    public Object evaluate(String expression) {
        return eval.evaluate(expression, variables);
    }

    /**
     * Evaluates an expression without using variables.
     *
     * @param expression the mathematical expression to evaluate.
     * @return the result of the evaluation as a Double.
     */
    @Override
    public Object evaluateWithoutVariables(String expression) {
        return eval.evaluate(expression);
    }

    /**
     * Evaluates an expression with custom functions and operators.
     * In this implementation, the factorial function and operator (!) are added.
     *
     * @param expression the mathematical expression to evaluate.
     * @return the result of the evaluation as a Double.
     */
    @Override
    public Object evaluateWithCustomFunc(String expression) {
        // Define a custom function for factorial
        Function FACTORIAL_FUNC = new Function("factorial", 1);

        // Define a custom operator for factorial (!)
        Operator FACTORIAL_OP = new Operator("!", 1, Operator.Associativity.LEFT, 5);

        // Get the default parameters of DoubleEvaluator
        Parameters params = DoubleEvaluator.getDefaultParameters();

        // Add custom factorial function and operator to the parameters
        params.add(FACTORIAL_FUNC);
        params.add(FACTORIAL_OP);

        // Create a custom evaluator subclass that handles the factorial function and operator
        DoubleEvaluator evaluator = new DoubleEvaluator(params) {

            /**
             * Overrides the evaluate method to handle the custom factorial function.
             */
            @Override
            protected Double evaluate(Function function, Iterator<Double> arguments, Object evaluationContext) {
                if (function == FACTORIAL_FUNC) {
                    // Calculate the factorial of the argument
                    long fact = factorial((int) Math.round(arguments.next()));
                    return (double) fact;
                } else {
                    // Delegate to the parent evaluator for other functions
                    return super.evaluate(function, arguments, evaluationContext);
                }
            }

            /**
             * Overrides the evaluate method to handle the custom factorial operator (!).
             */
            @Override
            protected Double evaluate(Operator operator, Iterator<Double> arguments, Object evaluationContext) {
                if (operator == FACTORIAL_OP) {
                    // Calculate the factorial of the argument
                    long fact = factorial((int) Math.round(arguments.next()));
                    return (double) fact;
                } else {
                    // Delegate to the parent evaluator for other operators
                    return super.evaluate(operator, arguments, evaluationContext);
                }
            }
        };

        // Evaluate the expression using the custom evaluator
        return evaluator.evaluate(expression);
    }

    /**
     * Adds or updates a variable in the variables map for use in expression evaluation.
     *
     * @param name     the name of the variable.
     * @param variable the value of the variable as a Double.
     */
    @Override
    public void setVariable(String name, Double variable) {
        variables.set(name, variable);
    }

    /**
     * Returns the name of this parser implementation as a String.
     *
     * @return the name of the parser ("Javaluator").
     */
    @Override
    public String toString() {
        return "Javaluator";
    }
}
