package org.example;

import org.example.strategies.EvalEx;
import org.example.strategies.IParser;

public class Main {
    public static void main(String[] args) {
        IParser parser = new EvalEx();
        System.out.println(parser.evaluate("1+3"));
    }
}