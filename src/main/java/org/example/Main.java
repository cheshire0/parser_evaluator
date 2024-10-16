package org.example;

import org.example.strategies.*;
import org.example.testers.DataTypeTest;
import org.example.testers.MemoryTest;
import org.example.testers.SpeedTest;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<IParser> parsers = new ArrayList<>();
        parsers.add(new Exp4j());
        parsers.add(new EvalEx());
        parsers.add(new Expr4j());

        DataTypeTest dataTypeTest = new DataTypeTest(parsers);
        dataTypeTest.test();
        //SpeedTest s = new SpeedTest(parsers);
        //s.test();

        MemoryTest m = new MemoryTest(parsers);
        m.test();
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