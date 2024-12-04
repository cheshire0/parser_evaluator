package org.parser.evaluator.util.log.report;

import org.parser.evaluator.strategies.IParser;
import org.parser.evaluator.testers.Test;

public class LogContext {
    private final Test test;
    private final IParser parser;
    private final String expression;
    private final Object result;

    public LogContext(Test test, IParser parser, String expression, Object result) {
        this.test = test;
        this.parser = parser;
        this.expression = expression;
        this.result = result;
    }

    public Test getTest() {
        return test;
    }

    public IParser getParser() {
        return parser;
    }

    public String getExpression() {
        return expression;
    }

    public Object getResult() {
        return result;
    }

    public String toString(boolean labels, String separator) {
        String baseString = String.format("Test: %s%sParser: %s%sExpression: %s%sResult: %s",
                test.toString(),
                separator,
                parser.getClass().getSimpleName(),
                separator,
                expression,
                separator,
                result != null ? result.toString() : "null");

        if (labels) {
            return baseString;  // Optionally add labels or customize further if needed
        }

        // If no labels are needed, just return the base string without labels
        return baseString.replace("Test:", "").replace("Parser:", "").replace("Expression:", "").replace("Result:", "").trim();
    }

}
