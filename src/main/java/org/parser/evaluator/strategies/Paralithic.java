package org.parser.evaluator.strategies;

import com.dfsek.paralithic.Expression;
import com.dfsek.paralithic.eval.parser.Parser;
import com.dfsek.paralithic.eval.parser.Scope;
import com.dfsek.paralithic.eval.tokenizer.ParseException;

import java.util.Map;

public class Paralithic implements IParser{

    @Override
    public Object evaluate(String expression) {
        Parser parser = new Parser(); // Create parser instance.
        Scope scope = new Scope(); // Create variable scope. This scope can hold both constants and invocation variables.
        for(String varName : variables.keySet()){
            Double varValue = variables.get(varName);
            scope.create(varName, varValue);
        }
        Expression expr = null;
        try {
            expr = parser.parse(expression, scope);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return expr.evaluate();
    }

    @Override
    public Object evaluateWithoutVariables(String expression) {
        Parser parser = new Parser(); // Create parser instance.
        Scope scope = new Scope();
        Expression expr = null;
        try {
            expr = parser.parse(expression, scope);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return expr.evaluate();
    }

    @Override
    public String toString() {
        return "Paralithic";
    }
}
