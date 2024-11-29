package org.parser.evaluator.strategies;

import com.fathzer.soft.javaluator.DoubleEvaluator;
import com.fathzer.soft.javaluator.Function;
import com.fathzer.soft.javaluator.Parameters;
import com.fathzer.soft.javaluator.StaticVariableSet;

import java.util.Iterator;

import static org.parser.evaluator.util.MathTestUtil.factorial;

public class Javaluator implements IParser{

    private final DoubleEvaluator eval = new DoubleEvaluator();
    private final StaticVariableSet<Double> variables = new StaticVariableSet<Double>();

    @Override
    public Object evaluate(String expression) {
        return eval.evaluate(expression, variables);
    }

    @Override
    public Object evaluateWithoutVariables(String expression) {
        return eval.evaluate(expression);
    }

    @Override
    public Object evaluateWithCustomFunc(String expression) {
        Function FACTORIAL = new Function("factorial", 1);
        // Gets the default DoubleEvaluator's parameters
        Parameters params = DoubleEvaluator.getDefaultParameters();
        params.add(FACTORIAL);
        // Create a new subclass of DoubleEvaluator that support the new function
        DoubleEvaluator evaluator = new DoubleEvaluator(params) {
            @Override
            protected Double evaluate(Function function, Iterator<Double> arguments, Object evaluationContext) {
                if (function == FACTORIAL) {
                    // Implements the new function
                    long fact = factorial((int) Math.round(arguments.next()));
                    return (double) fact;
                } else {
                    // If it's another function, pass it to DoubleEvaluator
                    return super.evaluate(function, arguments, evaluationContext);
                }
            }
        };
        return evaluator.evaluate(expression);
    }

    @Override
    public void setVariable(String name, Double variable){
        variables.set(name, variable);
    }

    @Override
    public String toString() {
        return "Javaluator";
    }
}
