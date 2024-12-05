package org.parser.evaluator;

import org.parser.evaluator.strategies.*;
import org.parser.evaluator.strategies.evalex.EvalEx;
import org.parser.evaluator.testers.extensibility.ExtensibilityTest;
import org.parser.evaluator.testers.extensibility.VariableTest;
import org.parser.evaluator.testers.mathematical.DataTypeTest;
import org.parser.evaluator.testers.mathematical.MathExpressionTest;
import org.parser.evaluator.testers.performance.MemoryTest;
import org.parser.evaluator.testers.performance.SpeedTest;
import org.parser.evaluator.util.log.OutputHandler;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Set up the OutputHandler to manage the test results output (console, CSV, and table)
        OutputHandler.setConfig(true, true, true);

        // Create an instance of Orchestrator to manage the execution of tests
        Orchestrator orchestrator = new Orchestrator();

        ArrayList<IParser> parsers = new ArrayList<>();
        parsers.add(new Exp4j());
        parsers.add(new EvalEx());
        parsers.add(new Expr4j());
        parsers.add(new Paralithic());
        parsers.add(new JavaMathExpressionParser());
        parsers.add(new Javaluator());

        orchestrator.setParsers(parsers);

        // Add different strategies
        orchestrator.addTest(new SpeedTest());
        orchestrator.addTest(new DataTypeTest());
        orchestrator.addTest(new MemoryTest());
        orchestrator.addTest(new MathExpressionTest());
        orchestrator.addTest(new VariableTest());
        orchestrator.addTest(new ExtensibilityTest());

        // Run all tests
        orchestrator.runTests();
    }
}