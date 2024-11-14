package org.parser.evaluator.testers;

import org.parser.evaluator.strategies.IParser;

import java.util.Map;

import static org.parser.evaluator.util.Logger.saveResultsToCSV;
import static org.parser.evaluator.util.MathTestUtil.areCloseEnough;
import static org.parser.evaluator.util.MathTestUtil.toDouble;

public class MathExpressionTest extends Test{

    private final double E = 2.718281828459045;

    private final Map<String, Double> validExpressions = Map.of(
            "3 + 2 * 4 - 1", 10.0,
            "sin(0)", 0.0,
            "cos(0)", 1.0,
            "2^3", 8.0,
            "2 * (3 + 5)", 16.0
    );

    //todo kijegyezni valahova, melyik működik -> adatok végül egy táblázatba
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
    protected Object testParser(IParser parser) {
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
                System.out.println("Exception while evaluating " + expression + ": " + exception.getMessage());
                saveResultsToCSV(this, parser, "Failed to evaluate expression: " + expression);
                continue;  // Skip to the next test if there's an exception
            }

            System.out.println(expression + " = " + result);
            saveResultsToCSV(this, parser, expression + " got parsed as: " + result);

            if(areCloseEnough(result, value)){
                passedTests++;
                System.out.println("Correctly evaluated");
            }
            else System.out.println("Incorrectly evaluated");
        }

        // Test log
        passedTests += testLogarithmicExpressions(parser)? 1 : 0;

        // Test invalid expressions
        for (Map.Entry<String, Class<? extends Exception>> entry : invalidExpressions.entrySet()) {
            String expression = entry.getKey();
            Class<? extends Exception> expectedException = entry.getValue();

            try {
                parser.evaluate(expression);
                System.out.println("Error: Expression '" + expression + "' did not throw an exception as expected.");
                saveResultsToCSV(this, parser, "Failed test - expected exception not thrown for: " + expression);
            } catch (Exception e) {
                passedTests++;
                System.out.println("Correctly threw " + expectedException.getSimpleName() + " for expression: " + expression);
                saveResultsToCSV(this, parser, "Correctly threw " + expectedException.getSimpleName() + " for expression: " + expression);
            }
        }

        double percentagePassed = (passedTests / numberOfTests) * 100;
        System.out.println(String.format("%.2f", percentagePassed) + "% of tests passed");
        saveResultsToCSV(this, parser, String.format("%.2f", percentagePassed) + "% of tested expressions got correctly parsed.");
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
                    System.out.println("Logarithmic expression '" + expression + "' evaluated correctly.");
                    saveResultsToCSV(this, parser, "Logarithmic expression '" + expression + "' evaluated correctly.");
                    returnValue = true;  // Test passed if one expression is evaluated correctly
                }
                else{
                    System.out.println("Logarithmic expression '" + expression + "' evaluated incorrectly. Expected value: " + expectedValue + " got value: " + result);
                    saveResultsToCSV(this, parser, "Logarithmic expression '" + expression + "' evaluated incorrectly. Expected value: " + expectedValue + " got value: " + result);
                }
            } catch (Exception e) {
                System.out.println("Exception while evaluating logarithmic expression " + expression + ": " + e.getMessage());
                saveResultsToCSV(this, parser, "Failed to evaluate logarithmic expression: " + expression);
            }
        }
        if(!returnValue) {
            System.out.println("No logarithmic expressions were evaluated correctly.");
            saveResultsToCSV(this, parser, "No logarithmic expressions were evaluated correctly.");
        }
        return returnValue;  // Test fails if none of the expressions are correct
    }

    @Override
    public String toString() {
        return "Mathematical Expressions";
    }
}
