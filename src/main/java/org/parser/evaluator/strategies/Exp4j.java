package org.parser.evaluator.strategies;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;

import static org.parser.evaluator.util.test.MathTestUtil.factorial;

public class Exp4j implements IParser{

    @Override
    public Object evaluate(String expression) {
        //System.out.println("before "+MemoryTest.getUsedMemory());
        Expression e = new ExpressionBuilder(expression)
                .variables(variables.keySet())
                .build()
                .setVariables(variables);
        //System.out.println("after "+MemoryTest.getUsedMemory());
        return e.evaluate();
    }

    @Override
    public Object evaluateWithoutVariables(String expression) {
        //System.out.println("before "+MemoryTest.getUsedMemory());
        Expression e = new ExpressionBuilder(expression).build();
        //System.out.println("after "+MemoryTest.getUsedMemory());
        return e.evaluate();
    }

    @Override
    public Object evaluateWithCustomFunc(String expression) {
        Function fact = new Function("factorial", 1) {

            @Override
            public double apply(double... args) {
                return factorial((int) args[0]);
            }
        };

        Operator operator = new Operator("!", 1, true, 10001) {
            @Override
            public double apply(double... args) {
                return factorial((int) args[0]);
            }
        };

        return new ExpressionBuilder(expression)
                .function(fact)
                .operator(operator)
                .build()
                .evaluate();
    }

    @Override
    public String toString() {
        return "Exp4j";
    }
}
