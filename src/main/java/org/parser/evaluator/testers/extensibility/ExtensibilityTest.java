package org.parser.evaluator.testers.extensibility;

import org.parser.evaluator.strategies.IParser;
import org.parser.evaluator.testers.Test;
import org.parser.evaluator.util.log.report.LogContext;
import org.parser.evaluator.util.log.OutputHandler;

import java.util.Map;
import static org.parser.evaluator.util.test.MathTestUtil.areCloseEnough;
import static org.parser.evaluator.util.test.MathTestUtil.toDouble;

public class ExtensibilityTest extends Test {

    private final Map<String, Double> funcExpressions = Map.of(
            "factorial(10)", 3628800.0,
            "factorial(5) + factorial(0)", 121.0,
            "factorial(10 - 3)", 5040.0
    );

    private final Map<String, Double> opExpressions = Map.of(
            "10!", 3628800.0,
            "5! + 0!", 121.0,
            "(10 - 3)!", 5040.0
    );

    private boolean testCustomFunction(String customType, IParser parser, Map.Entry<String, Double> entry) {
        String expression = entry.getKey();
        Double value = entry.getValue();

        boolean testPassed = false;
        try {
            // Evaluate the expression
            double result = toDouble(parser.evaluateWithCustomFunc(expression));
            OutputHandler.log("Expression '" + expression + "' with custom " + customType + " evaluated to: " + result);

            // Check if the result is close enough to the expected value
            if (areCloseEnough(result, value)) {
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

    @Override
    public Object testParser(IParser parser) {
        int numberOfTests = funcExpressions.size() + opExpressions.size();
        double passedTests = 0;

        for (Map.Entry<String, Double> entry : funcExpressions.entrySet()) {
            if (testCustomFunction("function", parser, entry)) {
                passedTests++;
            }
        }

        for (Map.Entry<String, Double> entry : opExpressions.entrySet()) {
            if (testCustomFunction("operator", parser, entry)) {
                passedTests++;
            }
        }

        // Summary of passed tests
        double percentagePassed = (passedTests / numberOfTests) * 100;
        OutputHandler.log(String.format("%.2f", percentagePassed) + "% of tests passed");
        return passedTests / numberOfTests;
    }

    @Override
    public String toString() {
        return "Extensibility";
    }
}
