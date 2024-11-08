package org.parser.evaluator.util;

import org.parser.evaluator.strategies.IParser;
import org.parser.evaluator.testers.Test;

import java.util.ArrayList;
import java.util.List;

public class Orchestrator {
    private List<IParser> parsers;
    private final List<Test> tests;

    public Orchestrator() {
        this.parsers = new ArrayList<>();
        this.tests = new ArrayList<>();
    }

    public void setParsers(List<IParser> parsers) {
        this.parsers = parsers;
    }

    public void addTest(Test test) {
        this.tests.add(test);
    }

    //run all strategies and collect results
    public List<Object> runTests() {
        List<Object> results = new ArrayList<>();
        for (Test test : tests) {
            results.add(test.toString());
            test.setParsers(parsers);
            results.add(test.test());
        }
        return results;
    }

}
