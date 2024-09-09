package org.example.strategies;

public interface IParser {
    Object evaluate(String expression);

    void addSource(Object source);
}
