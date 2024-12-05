package org.parser.evaluator;

import org.parser.evaluator.strategies.IParser;
import org.parser.evaluator.testers.Test;
import org.parser.evaluator.util.log.OutputHandler;

import java.util.ArrayList;
import java.util.List;

public class Orchestrator {

    // List of parsers that will be tested
    private List<IParser> parsers;

    // List of tests that will be run
    private final List<Test> tests;

    // Constructor initializes the parsers and tests lists
    public Orchestrator() {
        this.parsers = new ArrayList<>();
        this.tests = new ArrayList<>();
    }

    // Set the parsers to be used in the tests
    public void setParsers(List<IParser> parsers) {
        this.parsers = parsers;
    }

    // Add a test to the list of tests to be run
    public void addTest(Test test) {
        this.tests.add(test);
    }

    // Run all the added tests on the parsers
    public void runTests() {

        // Loop through all the tests
        for (Test test : tests) {
            OutputHandler.log("---------"+test+"---------\n");

            // Set the parsers for the current test
            test.setParsers(parsers);

            // Execute the test and collect results
            test.test();
        }
    }
}
