package org.example;

import org.example.strategies.*;
import org.example.testers.MemoryTest;
import org.example.testers.SpeedTest;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<IParser> parsers = new ArrayList<>();
        parsers.add(new EvalEx());
        parsers.add(new Exp4j());
        parsers.add(new Expr4j());

        SpeedTest s = new SpeedTest(parsers);
        s.test();

        MemoryTest m = new MemoryTest(parsers);
        m.test();
    }
}

//featurök, memória, speed, hibakezelés, stabilitas
//3 parser behuzasa