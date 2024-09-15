package org.example.strategies;

public interface IParser {
    String evaluate(String expression);

    void addSource(Object source);
}
