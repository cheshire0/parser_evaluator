package org.parser.evaluator;

import org.parser.evaluator.strategies.*;
import org.parser.evaluator.testers.*;
import org.parser.evaluator.util.Orchestrator;

import java.util.ArrayList;
import java.util.List;

import static org.parser.evaluator.util.Logger.saveResultsToCSV;

public class Main {
    public static void main(String[] args) {
        Orchestrator orchestrator = new Orchestrator();

        ArrayList<IParser> parsers = new ArrayList<>();
        parsers.add(new Exp4j());
        parsers.add(new EvalEx());
        parsers.add(new Expr4j());
        parsers.add(new Paralithic());
        parsers.add(new JavaMathExpressionParser());

        orchestrator.setParsers(parsers);

        // Add different strategies
        orchestrator.addTest(new SpeedTest());
        orchestrator.addTest(new DataTypeTest());
        orchestrator.addTest(new MemoryTest());
        orchestrator.addTest(new MathExpressionTest());
        orchestrator.addTest(new VariableTest());

        // Run all tests
        List<Object> results = orchestrator.runTests();
    }
}

//featurök, memória, speed, hibakezelés (pontosság), stabilitas (rossz bemenet)
//3 parser behuzasa

//parancssorból legyen futtatható, config fájl
//belső kör (egy példány sokszor fut), külső kör (sokszor példányosítom a parsert)
//10.000 * 1 vagy 1* 10.000
//automatikus kimentés csv fájlokba

//strategybe a különböző rugalmassági dimenziók: parserek, példák, hányszor, hogyan fut, mit értékelek ki (seb, mem...), (hova mentem ki)
//*rugalmasság*
//adatbázisba