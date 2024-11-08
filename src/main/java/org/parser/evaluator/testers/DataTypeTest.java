package org.parser.evaluator.testers;

import org.parser.evaluator.strategies.IParser;
import java.util.ArrayList;
import java.util.List;
import static org.parser.evaluator.util.Logger.saveResultsToCSV;

public class DataTypeTest extends Test {

    public DataTypeTest() {
        expressions = List.of("123", "45.7", "true", "false", "\"hello\"", "\"test string\"");
    }

    private boolean testType(IParser parser, String expressionStr) {
        try {
            System.out.println("Testing expression: " + expressionStr);
            Object result = parser.evaluate(expressionStr);

            System.out.println("Parsed result: " + result.toString());

            return checkDataType(parser, result, expressionStr);

        } catch (Exception e) {
            System.out.println("Parser "+parser.getClass().getSimpleName()+" failed for expression: " + expressionStr);
            return false;
        }
    }

    private void printOutput(Object result, Boolean condition, String type) {
        if (condition) {
            System.out.println("Success: Expression is parsed as a(n) "+type+".");
        } else {
            System.out.println("Error: Expected "+type+", but got " + result.getClass().getSimpleName());
        }
    }

    private boolean evaluateForType(IParser parser, Class<?> type, Object result) {
        boolean success = type.isInstance(result);
        saveResultsToCSV(this, parser, type.getSimpleName() + " type " + (success? "correctly parsed.":"incorrectly parsed."));
        printOutput(result, success, type.getSimpleName());
        return success;
    }

    // Method to check specific data types in the parsed result
    private boolean checkDataType(IParser parser, Object result, String expressionStr) {

        boolean success=false;

        if (expressionStr.matches("\\d+")) {
            success = evaluateForType(parser, Integer.class, result);

        } else if (expressionStr.matches("\\d+\\.\\d+")) {
            success = evaluateForType(parser, Double.class, result) || evaluateForType(parser, Float.class, result);

        } else if (expressionStr.equals("true") || expressionStr.equals("false")) {
            success = evaluateForType(parser, Boolean.class, result);

        } else if (expressionStr.matches("\".*\"")) { // Check for string literal
            success = evaluateForType(parser, String.class, result);
            
        } else {
            // Additional cases for other types
            System.out.println("Error: Unsupported data type or invalid expression.");
        }

        return success;
    }

    @Override
    protected Object testParser(IParser parser) {
        List<Boolean> results = new ArrayList<>();
        for(String expression : expressions) {
            results.add(testType(parser, expression));
        }
        double numberOfTrue = 0;
        for (Boolean result : results) {
            if(result) numberOfTrue++;
        }
        saveResultsToCSV(this, parser, String.format("%.2f", numberOfTrue/results.size() * 100) + "% of tested data types got correctly parsed.");
        return numberOfTrue/results.size();
    }

    @Override
    public String toString() {
        return "Data Type";
    }
}