package org.example;

import org.example.strategies.EvalEx;
import org.example.strategies.Exp4j;
import org.example.strategies.IParser;
import org.example.strategies.Jexl;
import org.example.testers.SpeedTest;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<IParser> parsers = new ArrayList<>();
        parsers.add(new EvalEx());
        parsers.add(new Exp4j());
        parsers.add(new Jexl());

        SpeedTest s = new SpeedTest(parsers);
        s.test();
    }
}

//featurök, memória, speed, hibakezelés, stabilitas
//3 parser behuzasa