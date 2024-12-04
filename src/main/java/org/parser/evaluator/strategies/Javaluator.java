package org.parser.evaluator.strategies;

import com.fathzer.soft.javaluator.*;

import java.util.Iterator;

import static org.parser.evaluator.util.test.MathTestUtil.factorial;

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
        Function FACTORIAL_FUNC = new Function("factorial", 1);
        Operator FACTORIAL_OP = new Operator("!", 1, Operator.Associativity.LEFT, 5);
        // Gets the default DoubleEvaluator's parameters
        Parameters params = DoubleEvaluator.getDefaultParameters();
        params.add(FACTORIAL_FUNC);
        params.add(FACTORIAL_OP);
        // Create a new subclass of DoubleEvaluator that support the new function
        DoubleEvaluator evaluator = new DoubleEvaluator(params) {
            @Override
            protected Double evaluate(Function function, Iterator<Double> arguments, Object evaluationContext) {
                if (function == FACTORIAL_FUNC) {
                    // Implements the new function
                    long fact = factorial((int) Math.round(arguments.next()));
                    return (double) fact;
                } else {
                    // If it's another function, pass it to DoubleEvaluator
                    return super.evaluate(function, arguments, evaluationContext);
                }
            }

            protected Double evaluate(Operator operator, Iterator<Double> arguments, Object evaluationContext) {
                if (operator == FACTORIAL_OP) {
                    // Implements the new function
                    long fact = factorial((int) Math.round(arguments.next()));
                    return (double) fact;
                } else {
                    // If it's another function, pass it to DoubleEvaluator
                    return super.evaluate(operator, arguments, evaluationContext);
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
