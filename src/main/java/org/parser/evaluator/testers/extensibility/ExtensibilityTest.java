package org.parser.evaluator.testers.extensibility;

import org.parser.evaluator.strategies.IParser;
import org.parser.evaluator.testers.Test;
import org.parser.evaluator.util.log.report.LogContext;
import org.parser.evaluator.util.log.OutputHandler;

import java.util.Map;
import static org.parser.evaluator.util.test.MathTestUtil.areCloseEnough;
import static org.parser.evaluator.util.test.MathTestUtil.toDouble;

public class ExtensibilityTest extends Test {

    // Expressions for testing custom functions (e.g., factorial).
    private final Map<String, Double> funcExpressions = Map.of(
            "factorial(10)", 3628800.0, // 10! = 3628800
            "factorial(5) + factorial(0)", 121.0, // 5! + 0! = 120 + 1 = 121
            "factorial(10 - 3)", 5040.0 // 7! = 5040
    );

    // Expressions for testing custom operators (e.g., factorial as a postfix operator).
    private final Map<String, Double> opExpressions = Map.of(
            "10!", 3628800.0, // 10! = 3628800
            "5! + 0!", 121.0, // 5! + 0! = 120 + 1 = 121
            "(10 - 3)!", 5040.0 // 7! = 5040
    );

    /**
     * Tests the parser's ability to evaluate custom functions or operators correctly.
     *
     * @param customType The type of the custom element being tested (function or operator).
     * @param parser The parser instance to evaluate the expressions.
     * @param entry The expression and its expected result.
     * @return boolean indicating whether the test passed.
     */
    private boolean testCustomFunction(String customType, IParser parser, Map.Entry<String, Double> entry) {
        String expression = entry.getKey();
        Double expectedValue = entry.getValue();

        boolean testPassed = false;
        try {
            // Evaluate the expression with the custom function/operator
            double result = toDouble(parser.evaluateWithCustomFunc(expression));
            OutputHandler.log("Expression '" + expression + "' with custom " + customType + " evaluated to: " + result);

            // Check if the result is close enough to the expected value
            if (areCloseEnough(result, expectedValue)) {
                testPassed = true;
                OutputHandler.log(new LogContext(this, parser, expression, "evaluated correctly"));
            } else {
                OutputHandler.log(new LogContext(this, parser, expression, "evaluated incorrectly"));
            }
        } catch (Exception e) {
            OutputHandler.log(new LogContext(this, parser, expression,"failed to evaluate"));
        }
        return testPassed;
    }

    /**
     * Runs the parser through all the defined test cases for custom functions and operators.
     *
     * @param parser The parser to be tested.
     * @return The fraction of tests passed (between 0 and 1).
     */
    @Override
    public Object testParser(IParser parser) {
        int numberOfTests = funcExpressions.size() + opExpressions.size(); // Total number of tests
        double passedTests = 0;

        // Run tests for custom functions (e.g., factorial)
        for (Map.Entry<String, Double> entry : funcExpressions.entrySet()) {
            if (testCustomFunction("function", parser, entry)) {
                passedTests++;
            }
        }

        // Run tests for custom operators (e.g., "10!")
        for (Map.Entry<String, Double> entry : opExpressions.entrySet()) {
            if (testCustomFunction("operator", parser, entry)) {
                passedTests++;
            }
        }

        // Summary of tests passed
        double percentagePassed = (passedTests / numberOfTests) * 100;
        OutputHandler.log(String.format("%.2f", percentagePassed) + "% of tests passed");
        return passedTests / numberOfTests;
    }

    // Return the name of the test for reporting purposes
    @Override
    public String toString() {
        return "Extensibility";
    }
}
