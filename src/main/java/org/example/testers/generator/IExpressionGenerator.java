package org.example.testers.generator;

import java.util.Random;
import java.util.Stack;

//todo interface-be és ne legyen static
//todo param: min max operator szám

public interface IExpressionGenerator {

    public String generate(int minOperators, int maxOperators);
}