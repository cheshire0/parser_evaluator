package org.parser.evaluator.testers.mathematical;

import org.parser.evaluator.strategies.IParser;
import org.parser.evaluator.testers.Test;
import org.parser.evaluator.util.log.report.LogContext;
import org.parser.evaluator.util.log.OutputHandler;

import java.util.Map;

import static org.parser.evaluator.util.test.MathTestUtil.areCloseEnough;
import static org.parser.evaluator.util.test.MathTestUtil.toDouble;

public class MathExpressionTest extends Test {

    private final double E = 2.718281828459045;

    private final Map<String, Double> validExpressions = Map.of(
            "3 + 2 * 4 - 1", 10.0,
            "sin(0)", 0.0,
            "cos(0)", 1.0,
            "2^3", 8.0,
            "2 * (3 + 5)", 16.0
    );

    private final Map<String, Double> logarithmicExpressions = Map.of(
            "log(10)", 2.3025850929940456840179914546844, //natural
            "log2(4)", 2.0,
            "log10(100)", 2.0,
            "ln(2.718281828459045)", 1.0,    // natural log, typically uses ln
            "lg(100)", 2.0                 // log base 10, may vary depending on parser syntax
    );

    private final Map<String, Class<? extends Exception>> invalidExpressions = Map.of(
            "1 / 0", ArithmeticException.class,
            "3 + * 5", IllegalArgumentException.class
    );

    @Override
    public Object testParser(IParser parser) {
        int numberOfTests = validExpressions.size() + 1 + invalidExpressions.size();
        double passedTests = 0;

        // Test valid expressions
        for(Map.Entry<String, Double> e : validExpressions.entrySet()){
            String expression = e.getKey();
            double value = e.getValue();

            double result;
            try {
                result = toDouble(parser.evaluate(expression));
            } catch (Exception exception) {

                OutputHandler.log(new LogContext(this, parser, expression,"failed to evaluate"));

                continue;  // Skip to the next test if there's an exception
            }
            OutputHandler.log(new LogContext(this, parser, expression, result));

            if(areCloseEnough(result, value)){
                passedTests++;
                OutputHandler.log("Correctly evaluated");

            }
            else OutputHandler.log("Incorrectly evaluated");
        }

        // Test log
        passedTests += testLogarithmicExpressions(parser)? 1 : 0;

        // Test invalid expressions
        for (Map.Entry<String, Class<? extends Exception>> entry : invalidExpressions.entrySet()) {
            String expression = entry.getKey();
            Class<? extends Exception> expectedException = entry.getValue();

            try {
                parser.evaluate(expression);
                OutputHandler.log(new LogContext(this, parser,expression, "failed test - expected exception not thrown"));

            } catch (Exception e) {
                passedTests++;
                OutputHandler.log(new LogContext(this, parser, expression, "Correctly threw " + expectedException.getSimpleName()));

            }
        }

        double percentagePassed = (passedTests / numberOfTests) * 100;
        OutputHandler.log(String.format("%.2f", percentagePassed) + "% of tested expressions got correctly parsed.");

        return passedTests / numberOfTests;
    }

    // Method to test logarithmic expressions
    private boolean testLogarithmicExpressions(IParser parser) {
        boolean returnValue = false;
        for (Map.Entry<String, Double> entry : logarithmicExpressions.entrySet()) {
            String expression = entry.getKey();
            double expectedValue = entry.getValue();
            try {
                double result = toDouble(parser.evaluate(expression));
                if (areCloseEnough(result, expectedValue)) {
                    OutputHandler.log(new LogContext(this, parser, expression, "evaluated correctly"));
                    returnValue = true;  // Test passed if one expression is evaluated correctly
                }
                else{
                    OutputHandler.log(new LogContext(this, parser, expression,"evaluated incorrectly, expected value: " + expectedValue + " got value: " + result));
                }

            } catch (Exception e) {
                OutputHandler.log(new LogContext(this, parser, expression, "failed to evaluate"));
            }
        }
        if(!returnValue) {
            OutputHandler.log(new LogContext(this, parser, "logarithmic expressions","none were evaluated correctly"));
        }
        return returnValue;  // Test fails if none of the expressions are correct
    }

    @Override
    public String toString() {
        return "Mathematical Expressions";
    }
}
