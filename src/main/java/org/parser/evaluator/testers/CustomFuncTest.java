package org.parser.evaluator.testers;

import org.parser.evaluator.strategies.IParser;
import java.util.Map;
import static org.parser.evaluator.util.Logger.saveResultsToCSV;
import static org.parser.evaluator.util.MathTestUtil.areCloseEnough;
import static org.parser.evaluator.util.MathTestUtil.toDouble;

public class CustomFuncTest extends Test{

    private final Map<String, Double> expressions = Map.of(
            "factorial(10)", 3628800.0,
            "factorial(5) + factorial(0)", 121.0,
            "factorial(10 - 3)", 5040.0
    );

    private boolean testCustomFunction(IParser parser, Map.Entry<String, Double> entry) {
        String expression = entry.getKey();
        Double value = entry.getValue();

        boolean testPassed = false;
        try {
            // Evaluate the expression
            double result = toDouble(parser.evaluateWithCustomFunc(expression));
            System.out.println("Expression '" + expression + "' with custom function evaluated to: " + result);

            // Check if the result is close enough to the expected value
            if (areCloseEnough(result, value)) {
                testPassed = true;
                System.out.println("Custom function expression '" + expression + "' evaluated correctly.");
                saveResultsToCSV(this, parser, "Variable expression '" + expression + "' evaluated correctly.");
            } else {
                System.out.println("Custom function expression '" + expression + "' did not evaluate as expected.");
                saveResultsToCSV(this, parser, "Variable expression '" + expression + "' evaluated incorrectly.");
            }
        } catch (Exception e) {
            System.out.println("Exception while evaluating custom function expression '" + expression + "': " + e.getMessage());
            saveResultsToCSV(this, parser, "Failed to evaluate custom function expression: " + expression);
        }
        return testPassed;
    }

    @Override
    protected Object testParser(IParser parser) {
        int numberOfTests = expressions.size();
        double passedTests = 0;

        for (Map.Entry<String, Double> entry : expressions.entrySet()) {
            if (testCustomFunction(parser, entry)) {
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
        return "Custom Function Operations";
    }
}
