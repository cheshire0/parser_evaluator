package org.parser.evaluator.strategies;

import com.dfsek.paralithic.Expression;
import com.dfsek.paralithic.eval.parser.Parser;
import com.dfsek.paralithic.eval.parser.Scope;
import com.dfsek.paralithic.eval.tokenizer.ParseException;
import com.dfsek.paralithic.functions.Function;
import com.dfsek.paralithic.functions.natives.NativeFunction;
import com.dfsek.paralithic.node.Statefulness;
import org.jetbrains.annotations.NotNull;
import org.parser.evaluator.util.test.MathTestUtil;

import java.lang.reflect.Method;

/**
 * The Paralithic class implements the IParser interface for evaluating
 * mathematical expressions using the Paralithic library.
 * It supports expressions with variables and custom functions.
 */
public class Paralithic implements IParser {

    /**
     * Evaluates an expression with variables defined in the variable map.
     *
     * @param expression The mathematical expression to evaluate.
     * @return The result of the evaluation as an Object (typically a Double).
     */
    @Override
    public Object evaluate(String expression) {
        Parser parser = new Parser(); // Create a parser instance.
        Scope scope = new Scope(); // Create a variable scope.

        // Populate the scope with variables from the variable map.
        for (String varName : variables.keySet()) {
            Double varValue = variables.get(varName);
            scope.create(varName, varValue);
        }

        Expression expr;
        try {
            // Parse the expression with the provided variables.
            expr = parser.parse(expression, scope);
        } catch (ParseException e) {
            // Handle parsing exceptions.
            throw new RuntimeException(e);
        }

        // Evaluate the parsed expression.
        return expr.evaluate();
    }

    /**
     * Evaluates an expression without any variables.
     *
     * @param expression The mathematical expression to evaluate.
     * @return The result of the evaluation as an Object (typically a Double).
     */
    @Override
    public Object evaluateWithoutVariables(String expression) {
        Parser parser = new Parser(); // Create a parser instance.
        Scope scope = new Scope(); // Create an empty scope (no variables).

        Expression expr;
        try {
            // Parse the expression without variables.
            expr = parser.parse(expression, scope);
        } catch (ParseException e) {
            // Handle parsing exceptions.
            throw new RuntimeException(e);
        }

        // Evaluate the parsed expression.
        return expr.evaluate();
    }

    /**
     * Evaluates an expression with a custom function, such as factorial.
     *
     * @param expression The mathematical expression to evaluate.
     * @return The result of the evaluation as an Object (typically a Double).
     */
    @Override
    public Object evaluateWithCustomFunc(String expression) {
        // Define the custom factorial function using NativeFunction.
        Function fact = new NativeFunction() {
            @Override
            public Method getMethod() throws NoSuchMethodException {
                // Bind the factorial function from MathTestUtil.
                return MathTestUtil.class.getMethod("factorial", double.class);
            }

            @Override
            public int getArgNumber() {
                // The factorial function takes one argument.
                return 1;
            }

            @Override
            public @NotNull Statefulness statefulness() {
                // Define the function as stateless.
                return Statefulness.STATELESS;
            }
        };

        Parser parser = new Parser(); // Create a parser instance.
        parser.registerFunction("factorial", fact); // Register the custom factorial function.

        Scope scope = new Scope(); // Create an empty scope.

        Expression expr;
        try {
            // Parse the expression with the custom function.
            expr = parser.parse(expression, scope);
        } catch (ParseException e) {
            // Handle parsing exceptions.
            throw new RuntimeException(e);
        }

        // Evaluate the parsed expression.
        return expr.evaluate();
    }

    /**
     * Returns the name of this parser implementation.
     *
     * @return The name of the parser ("Paralithic").
     */
    @Override
    public String toString() {
        return "Paralithic";
    }
}
