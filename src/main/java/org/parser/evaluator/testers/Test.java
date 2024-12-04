package org.parser.evaluator.testers;

import org.parser.evaluator.strategies.IParser;

import java.util.ArrayList;
import java.util.List;

public abstract class Test {
    protected boolean systemOutput =  true;

    protected final ArrayList<IParser> parsers = new ArrayList<>();

    protected List<String> expressions;

    public void setParsers(List<IParser> parsers) {
        this.parsers.clear();
        this.parsers.addAll(parsers);
    }

    public void setSystemOutput(boolean systemOutput) {
        this.systemOutput = systemOutput;
    }

    protected List<IParser> getParsers() {
        return this.parsers;
    }

    public List<Object> test(){
        if(parsers.isEmpty()) {
            throw new RuntimeException("No parsers set");
        }
        List<Object> results = new ArrayList<>();
        for (IParser parser : parsers) {
            System.out.println("Testing parser: " + parser.getClass().getSimpleName());
            results.add(testParser(parser));
            System.out.println();
        }
        return results;
    }

    public abstract Object testParser(IParser parser);
    public abstract String toString();
}
