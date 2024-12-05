package org.parser.evaluator.testers.extensibility;

import org.parser.evaluator.strategies.IParser;
import org.parser.evaluator.testers.Test;
import org.parser.evaluator.util.log.report.LogContext;
import org.parser.evaluator.util.log.OutputHandler;

import java.util.Map;
import static org.parser.evaluator.util.test.MathTestUtil.areCloseEnough;
import static org.parser.evaluator.util.test.MathTestUtil.toDouble;

public class VariableTest extends Test {

    // A map that holds expressions with variables, their values, and the expected results
    private final Map<String, VariableTestCase> variableExpressions = Map.of(
            "2 * x + 3", new VariableTestCase(Map.of("x", 4.0), 11.0),  // Example: x = 4, expected result = 11
            "a * b + c", new VariableTestCase(Map.of("a", 2.0, "b", 3.0, "c", 1.0), 7.0),  // Example: a = 2, b = 3, c = 1, expected result = 7
            "x^2 + y^2", new VariableTestCase(Map.of("x", 3.0, "y", 4.0), 25.0)  // Example: x = 3, y = 4, expected result = 25
    );

    /**
     * Method to test expressions with variables in the parser.
     *
     * @param parser The parser being tested.
     * @param expression The mathematical expression to evaluate.
     * @param testCase The test case containing variables and their expected result.
     * @return A boolean indicating whether the test passed or not.
     */
    private boolean testVariableExpressions(IParser parser, String expression, VariableTestCase testCase) {
        boolean testPassed = false;
        try {
            // Set the variables in the parser before evaluation
            for (Map.Entry<String, Double> variable : testCase.variables.entrySet()) {
                parser.setVariable(variable.getKey(), variable.getValue());
            }

            // Evaluate the expression and convert the result to double
            double result = toDouble(parser.evaluate(expression));

            // Check if the result is close enough to the expected value (floating-point comparison)
            if (areCloseEnough(result, testCase.expectedValue)) {
                testPassed = true;
                OutputHandler.log(new LogContext(this, parser, expression, "evaluated correctly"));
            } else {
                OutputHandler.log(new LogContext(this, parser, expression, "evaluated incorrectly"));
            }
        } catch (Exception e) {
            // If an exception occurs during evaluation, log the failure
            OutputHandler.log(new LogContext(this, parser, expression, "failed to evaluate"));
        }
        return testPassed;
    }

    // Custom inner class to store the variables and the expected result for each test case
    private static class VariableTestCase {
        Map<String, Double> variables;  // A map of variable names and their corresponding values
        double expectedValue;  // The expected result of evaluating the expression

        // Constructor to initialize the variables and the expected result
        VariableTestCase(Map<String, Double> variables, double expectedValue) {
            this.variables = variables;
            this.expectedValue = expectedValue;
        }
    }

    /**
     * Main testing method that runs the tests on the given parser.
     *
     * @param parser The parser being tested.
     * @return The percentage of passed tests (as a decimal value between 0 and 1).
     */
    @Override
    public Object testParser(IParser parser) {
        int numberOfTests = variableExpressions.size();  // Total number of tests to run
        double passedTests = 0;  // Counter for the number of tests passed

        // Iterate over all the expressions and test each one
        for (Map.Entry<String, VariableTestCase> entry : variableExpressions.entrySet()) {
            String expression = entry.getKey();  // The expression to evaluate
            VariableTestCase testCase = entry.getValue();  // The corresponding test case

            // Test each expression with the parser
            if (testVariableExpressions(parser, expression, testCase)) {
                passedTests++;  // Increment the passed tests count if the test passed
            }
        }

        // Calculate the percentage of tests passed
        double percentagePassed = (passedTests / numberOfTests) * 100;
        OutputHandler.log(String.format("%.2f", percentagePassed) + "% of tested expressions got correctly parsed.");

        // Return the percentage of passed tests as a decimal (fraction)
        return passedTests / numberOfTests;
    }

    // Return the name of the test for reporting purposes
    @Override
    public String toString() {
        return "Variable";  // Identifies this test as a "Variable" test
    }
}
