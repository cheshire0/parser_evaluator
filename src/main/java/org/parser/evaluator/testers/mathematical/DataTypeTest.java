package org.parser.evaluator.testers.mathematical;

import org.parser.evaluator.strategies.IParser;
import org.parser.evaluator.testers.Test;
import org.parser.evaluator.util.log.OutputHandler;
import org.parser.evaluator.util.log.report.LogContext;

import java.util.ArrayList;
import java.util.List;

public class DataTypeTest extends Test {

    // List of expressions to test data type parsing, including integers, floating points, booleans, and strings
    public DataTypeTest() {
        expressions = List.of("123", "45.7", "true", "false", "\"hello\"", "\"test string\"");
    }

    // Method to test a single expression and check its parsed data type
    private boolean testType(IParser parser, String expressionStr) {
        try {
            // Log the expression being tested
            OutputHandler.log("Testing expression: " + expressionStr);

            // Evaluate the expression using the parser
            Object result = parser.evaluate(expressionStr);

            // Log the result of the evaluation
            OutputHandler.log("Parsed result: " + result.toString());

            // Check if the result matches the expected data type
            return checkDataType(parser, result, expressionStr);

        } catch (Exception e) {
            // If the parser throws an exception, log the failure
            OutputHandler.log("Parser "+parser.getClass().getSimpleName()+" failed for expression: " + expressionStr);
            return false;
        }
    }

    // Method to log the output based on the data type check result
    private void printOutput(Object result, Boolean condition, String type) {
        if (condition) {
            // If the result is of the expected type, log success
            OutputHandler.log("Success: Expression is parsed as a(n) "+type+".");
        } else {
            // If the result is of the wrong type, log an error
            OutputHandler.log("Error: Expected "+type+", but got " + result.getClass().getSimpleName());
        }
    }

    // Method to check if the parsed result matches the expected type (e.g., Integer, Double, etc.)
    private boolean evaluateForType(IParser parser, Class<?> type, Object result) {
        boolean success = type.isInstance(result); // Check if result is an instance of the expected type
        OutputHandler.log(new LogContext(this, parser, type.getSimpleName() + " type ", (success? "correctly parsed":"incorrectly parsed")));
        printOutput(result, success, type.getSimpleName());
        return success;
    }

    // Method to determine the expected data type based on the expression string
    private boolean checkDataType(IParser parser, Object result, String expressionStr) {
        boolean success = false;

        // Check if the expression matches a specific pattern and evaluate its data type accordingly
        if (expressionStr.matches("\\d+")) {  // Integer match (e.g., "123")
            success = evaluateForType(parser, Integer.class, result);

        } else if (expressionStr.matches("\\d+\\.\\d+")) {  // Float/Double match (e.g., "45.7")
            success = evaluateForType(parser, Double.class, result) || evaluateForType(parser, Float.class, result);

        } else if (expressionStr.equals("true") || expressionStr.equals("false")) {  // Boolean match
            success = evaluateForType(parser, Boolean.class, result);

        } else if (expressionStr.matches("\".*\"")) {  // String literal match (e.g., "\"hello\"")
            success = evaluateForType(parser, String.class, result);

        } else {
            // If the expression doesn't match any known data type, log an error
            OutputHandler.log("Error: Unsupported data type or invalid expression.");
        }

        return success;
    }

    // Main method that tests all expressions against the provided parser
    @Override
    public Object testParser(IParser parser) {
        List<Boolean> results = new ArrayList<>();
        // Test each expression in the list
        for(String expression : expressions) {
            results.add(testType(parser, expression));
        }

        // Calculate and log the percentage of successful tests
        double numberOfTrue = 0;
        for (Boolean result : results) {
            if(result) numberOfTrue++;
        }

        // Log the percentage of tests passed
        OutputHandler.log(new LogContext(this, parser, "% of tested data types got correctly parsed", String.format("%.2f", numberOfTrue/results.size() * 100) ));
        return numberOfTrue/results.size();
    }

    // Return the name of the test (for reporting)
    @Override
    public String toString() {
        return "Data Type";
    }
}
