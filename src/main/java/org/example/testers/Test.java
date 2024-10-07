package org.example.testers;

import org.example.strategies.IParser;

import java.util.ArrayList;

public abstract class Test {
    //TODO for the sake of readability
    protected final ArrayList<IParser> parsers;

    protected String[] expressions;

    public Test(ArrayList<IParser> parsers) {
        this.parsers = parsers;
    }

    public void test(){
        for (IParser parser : parsers) {
            System.out.println("Testing parser: " + parser.getClass().getName());
            for (String expressionStr : expressions) {
                System.out.println("Expression: " + expressionStr);
                System.out.println("Result: " + parser.evaluate(expressionStr));

                testParser(parser, expressionStr);
            }
            System.out.println();
        }
    }

    protected abstract void testParser(IParser parser, String expressionStr);
}