package org.parser.evaluator.testers;

import org.parser.evaluator.strategies.IParser;

import java.util.ArrayList;
import java.util.List;

public abstract class Test {

    // List of parsers that will be tested
    protected final ArrayList<IParser> parsers = new ArrayList<>();

    // List of expressions to be evaluated during the tests
    protected List<String> expressions;

    // Method to set the parsers to be used in the tests
    public void setParsers(List<IParser> parsers) {
        this.parsers.clear();  // Clear any existing parsers
        this.parsers.addAll(parsers);  // Add the new parsers to the list
    }

    // Main method to run the test on all parsers and return the results
    public List<Object> test(){
        if(parsers.isEmpty()) {
            // If no parsers are set, throw an exception
            throw new RuntimeException("No parsers set");
        }

        List<Object> results = new ArrayList<>();  // List to hold results of the tests
        for (IParser parser : parsers) {
            // Print out which parser is being tested
            System.out.println("Testing parser: " + parser.getClass().getSimpleName());
            // Run the test on the current parser and store the result
            results.add(testParser(parser));
            System.out.println();
        }

        // Return the results of all the tests
        return results;
    }

    // Abstract method to test a specific parser. Implemented by subclasses.
    public abstract Object testParser(IParser parser);

    // Abstract method to return a string representation of the test.
    // Implemented by subclasses to provide the name of the test.
    public abstract String toString();
}
