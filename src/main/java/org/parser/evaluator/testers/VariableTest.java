package org.parser.evaluator.testers;

import org.parser.evaluator.strategies.IParser;
import java.util.Map;
import static org.parser.evaluator.util.Logger.saveResultsToCSV;
import static org.parser.evaluator.util.MathTestUtil.areCloseEnough;
import static org.parser.evaluator.util.MathTestUtil.toDouble;

public class VariableTest extends Test {

    // Expressions with variables, their values, and expected results
    private final Map<String, VariableTestCase> variableExpressions = Map.of(
            "2 * x + 3", new VariableTestCase(Map.of("x", 4.0), 11.0),
            "a * b + c", new VariableTestCase(Map.of("a", 2.0, "b", 3.0, "c", 1.0), 7.0),
            "x^2 + y^2", new VariableTestCase(Map.of("x", 3.0, "y", 4.0), 25.0)
    );

    // Method to test expressions with variables
    private boolean testVariableExpressions(IParser parser, String expression, VariableTestCase testCase) {
        boolean testPassed = false;
        try {
            // Set the variables in the parser
            for (Map.Entry<String, Double> variable : testCase.variables.entrySet()) {
                parser.setVariable(variable.getKey(), variable.getValue());
            }

            // Evaluate the expression
            double result = toDouble(parser.evaluate(expression));
            System.out.println("Expression '" + expression + "' with variables evaluated to: " + result);

            // Check if the result is close enough to the expected value
            if (areCloseEnough(result, testCase.expectedValue)) {
                testPassed = true;
                System.out.println("Variable expression '" + expression + "' evaluated correctly.");
                saveResultsToCSV(this, parser, "Variable expression '" + expression + "' evaluated correctly.");
            } else {
                System.out.println("Variable expression '" + expression + "' did not evaluate as expected.");
                saveResultsToCSV(this, parser, "Variable expression '" + expression + "' evaluated incorrectly.");
            }
        } catch (Exception e) {
            System.out.println("Exception while evaluating variable expression '" + expression + "': " + e.getMessage());
            saveResultsToCSV(this, parser, "Failed to evaluate variable expression: " + expression);
        }
        return testPassed;
    }

    // Custom inner class to hold variables and expected result
    private static class VariableTestCase {
        Map<String, Double> variables;
        double expectedValue;

        VariableTestCase(Map<String, Double> variables, double expectedValue) {
            this.variables = variables;
            this.expectedValue = expectedValue;
        }
    }

    // Main testing method
    @Override
    protected Object testParser(IParser parser) {
        int numberOfTests = variableExpressions.size();
        double passedTests = 0;

        for (Map.Entry<String, VariableTestCase> entry : variableExpressions.entrySet()) {
            String expression = entry.getKey();
            VariableTestCase testCase = entry.getValue();

            if (testVariableExpressions(parser, expression, testCase)) {
                passedTests++;
            }
        }

        // Summary of passed tests
        double percentagePassed = (passedTests / numberOfTests) * 100;
        System.out.println(String.format("%.2f", percentagePassed) + "% of tests passed");
        saveResultsToCSV(this, parser, String.format("%.2f", percentagePassed) + "% of tested expressions got correctly parsed.");
        return passedTests / numberOfTests;
    }

    @Override
    public String toString() {
        return "Variable";
    }
}
