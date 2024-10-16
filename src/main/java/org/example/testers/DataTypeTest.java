package org.example.testers;

import org.example.strategies.IParser;

import java.util.ArrayList;
import java.util.List;

public class DataTypeTest extends Test {

    public DataTypeTest(ArrayList<IParser> parsers) {
        super(parsers);
        expressions= List.of("123", "45.7", "true", "false");
    }

    @Override
    protected void testParser(IParser parser, String expressionStr) {
        try {
            System.out.println("Testing expression: " + expressionStr);
            Object result = parser.evaluate(expressionStr);

            System.out.println("Parsed result: " + result.toString());

            // Optionally add more custom checks for specific data types here
            checkDataType(parser, result, expressionStr);

        } catch (Exception e) {
            System.out.println("Parser "+parser.getClass().getSimpleName()+" failed for expression: " + expressionStr);
        }
    }

    private void printOutput(Object result, Boolean condition, String type) {
        if (condition) {
            System.out.println("Success: Expression is parsed as a(n) "+type+".");
        } else {
            System.out.println("Error: Expected "+type+", but got " + result.getClass().getSimpleName());
        }
    }

    // Method to check specific data types in the parsed result
    private void checkDataType(IParser parser, Object result, String expressionStr) {
        // Define expected data types you want to test
        if (expressionStr.matches("\\d+")) {
            // Test for integer
            printOutput(result, result instanceof Integer, "Integer");
        } else if (expressionStr.matches("\\d+\\.\\d+")) {
            // Test for float or double
            printOutput(result, (result instanceof Float || result instanceof Double), "Float/Double");
        } else if (expressionStr.equals("true") || expressionStr.equals("false")) {
            // Test for boolean
            printOutput(result, result instanceof Boolean, "Boolean");
        } else {
            // Additional cases for other types like strings, characters, etc.
            System.out.println("Error: Unsupported data type or invalid expression.");
        }
    }
}