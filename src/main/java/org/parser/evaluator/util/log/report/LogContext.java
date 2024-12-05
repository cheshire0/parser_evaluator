package org.parser.evaluator.util.log.report;

import org.parser.evaluator.strategies.IParser;
import org.parser.evaluator.testers.Test;

public record LogContext(Test test, IParser parser, String expression, Object result) {

    // Method to generate a string representation of the LogContext
    public String toString(boolean labels, String separator) {
        // Construct the log message using formatted strings
        String baseString = String.format("Test: %s%sParser: %s%sExpression: %s%sResult: %s",
                test.toString(),                         // Test name
                separator,
                parser.getClass().getSimpleName(),       // Parser class name
                separator,
                expression,                              // The expression being evaluated
                separator,
                result != null ? result.toString() : "null");  // The result or "null" if result is null

        // If labels are needed, return the base string as is
        if (labels) {
            return baseString;
        }

        // If no labels are needed, remove the labels and return the values
        return baseString.replace("Test:", "").replace("Parser:", "")
                .replace("Expression:", "").replace("Result:", "").trim();
    }
}
