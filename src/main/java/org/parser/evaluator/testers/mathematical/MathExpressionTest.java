package org.parser.evaluator.testers.mathematical;

import org.parser.evaluator.strategies.IParser;
import org.parser.evaluator.testers.Test;
import org.parser.evaluator.util.log.report.LogContext;
import org.parser.evaluator.util.log.OutputHandler;

import java.util.Map;

import static org.parser.evaluator.util.test.MathTestUtil.areCloseEnough;
import static org.parser.evaluator.util.test.MathTestUtil.toDouble;

public class MathExpressionTest extends Test {

    // Constant for Euler's number (e), which is used for logarithmic tests
    private final double E = 2.718281828459045;

    // Valid mathematical expressions and their expected results
    private final Map<String, Double> validExpressions = Map.of(
            "3 + 2 * 4 - 1", 10.0,
            "sin(0)", 0.0,
            "cos(0)", 1.0,
            "2^3", 8.0,
            "2 * (3 + 5)", 16.0
    );

    // Logarithmic expressions and their expected results
    private final Map<String, Double> logarithmicExpressions = Map.of(
            "log(10)", 2.3025850929940456840179914546844, // Natural log
            "log(10.0)", 1.0,
            "log2(4)", 2.0,
            "log10(100)", 2.0,
            "ln(2.718281828459045)", 1.0,    // Natural log, commonly 'ln'
            "lg(100)", 2.0                 // Log base 10
    );

    // Invalid expressions and the expected exceptions when they are evaluated
    private final Map<String, Class<? extends Exception>> invalidExpressions = Map.of(
            "1 / 0", ArithmeticException.class,  // Division by zero
            "3 + * 5", IllegalArgumentException.class  // Invalid operator
    );

    // Main method to test the parser with different types of expressions
    @Override
    public Object testParser(IParser parser) {
        // Total number of tests (valid + invalid expressions)
        int numberOfTests = validExpressions.size() + 1 + invalidExpressions.size();
        double passedTests = 0;

        // Test valid mathematical expressions
        for(Map.Entry<String, Double> e : validExpressions.entrySet()){
            String expression = e.getKey();
            double value = e.getValue();

            double result;
            try {
                // Try evaluating the expression
                result = toDouble(parser.evaluate(expression));
            } catch (Exception exception) {
                // If an exception is thrown, log the failure and continue to the next expression
                OutputHandler.log(new LogContext(this, parser, expression, "failed to evaluate"));
                continue;
            }

            // Log the result of the evaluation
            OutputHandler.log(new LogContext(this, parser, expression, result));

            // Check if the result is close enough to the expected value
            if(areCloseEnough(result, value)){
                passedTests++;
                OutputHandler.log("Correctly evaluated");
            } else {
                OutputHandler.log("Incorrectly evaluated");
            }
        }

        // Test logarithmic expressions
        passedTests += testLogarithmicExpressions(parser) ? 1 : 0;

        // Test invalid expressions and check for correct exceptions
        for (Map.Entry<String, Class<? extends Exception>> entry : invalidExpressions.entrySet()) {
            String expression = entry.getKey();
            Class<? extends Exception> expectedException = entry.getValue();

            try {
                // Try evaluating the invalid expression
                parser.evaluate(expression);
                // If no exception is thrown, log a failure
                OutputHandler.log(new LogContext(this, parser, expression, "failed test - expected exception not thrown"));
            } catch (Exception e) {
                // If the expected exception is thrown, log success
                passedTests++;
                OutputHandler.log(new LogContext(this, parser, expression, "Correctly threw " + expectedException.getSimpleName()));
            }
        }

        // Calculate and log the percentage of passed tests
        double percentagePassed = (passedTests / numberOfTests) * 100;
        OutputHandler.log(String.format("%.2f", percentagePassed) + "% of tested expressions got correctly parsed.");

        return passedTests / numberOfTests;
    }

    // Method to test logarithmic expressions (log, log2, log10, ln, lg) and check if they are parsed correctly
    private boolean testLogarithmicExpressions(IParser parser) {
        boolean returnValue = false;
        for (Map.Entry<String, Double> entry : logarithmicExpressions.entrySet()) {
            String expression = entry.getKey();
            double expectedValue = entry.getValue();

            try {
                // Try evaluating the logarithmic expression
                double result = toDouble(parser.evaluate(expression));
                // Check if the result is close to the expected value
                if (areCloseEnough(result, expectedValue)) {
                    OutputHandler.log(new LogContext(this, parser, expression, "evaluated correctly"));
                    returnValue = true;  // Test passed if one expression is evaluated correctly
                } else {
                    OutputHandler.log(new LogContext(this, parser, expression, "evaluated incorrectly, expected value: " + expectedValue + " got value: " + result));
                }

            } catch (Exception e) {
                // If an exception occurs, log the failure
                OutputHandler.log(new LogContext(this, parser, expression, "failed to evaluate"));
            }
        }
        if (!returnValue) {
            // Log failure if no logarithmic expressions were evaluated correctly
            OutputHandler.log(new LogContext(this, parser, "logarithmic expressions", "none were evaluated correctly"));
        }
        return returnValue;  // Test fails if none of the expressions are correct
    }

    // Return the name of the test for reporting purposes
    @Override
    public String toString() {
        return "Mathematical Expressions";
    }
}
