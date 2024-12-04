package org.parser.evaluator.strategies;

import com.dfsek.paralithic.Expression;
import com.dfsek.paralithic.eval.parser.Parser;
import com.dfsek.paralithic.eval.parser.Scope;
import com.dfsek.paralithic.eval.tokenizer.ParseException;
import com.dfsek.paralithic.functions.Function;
import com.dfsek.paralithic.functions.natives.NativeFunction;
import com.dfsek.paralithic.node.Statefulness;
import org.jetbrains.annotations.NotNull;
import org.parser.evaluator.util.test.MathTestUtil;

import java.lang.reflect.Method;

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
    public Object evaluateWithCustomFunc(String expression) {
        Function fact = new NativeFunction() {
            @Override
            public Method getMethod() throws NoSuchMethodException {
                return MathTestUtil.class.getMethod("factorial", double.class);
            }

            @Override
            public int getArgNumber() {
                return 1;
            }

            @Override
            public @NotNull Statefulness statefulness() {
                return Statefulness.STATELESS;
            }
        };
        Parser parser = new Parser(); // Create parser instance.
        parser.registerFunction("factorial", fact);
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
