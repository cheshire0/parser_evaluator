package org.parser.evaluator.testers.generator;

import java.util.Random;

public class ExpressionGenerator implements IExpressionGenerator{
    private final Random rand = new Random(42);
    public int chance = 60;

    private final String[] binaryOperators = {
            "+",
            "-",
            "*",
            //"/" expression's gotta be correct
    };

    //todo is it needed?
    private final String[] unaryOperators = {
            "cos",
            "sin"
    };

    public String generate(int opNumber){
        StringBuilder expression = new StringBuilder();

        expression.append(rand.nextDouble()*1000);

        for(int i=0; i<opNumber; i++){
            expression.append(addBinaryOperator());
        }

        return expression.toString();
    }

    public String addOperand(){
        if(rand.nextInt(100)<chance){
            return String.valueOf(rand.nextDouble()*1000);
        }
        else {
            return addUnaryOperator();
        }
    }

    public String addBinaryOperator(){
        StringBuilder operand= new StringBuilder();

        operand.append(" ");
        operand.append(binaryOperators[rand.nextInt(binaryOperators.length)]);
        operand.append(" ");
        operand.append(addOperand());

        return operand.toString();
    }

    public String addUnaryOperator(){
        StringBuilder operand= new StringBuilder();
        operand.append(unaryOperators[rand.nextInt(unaryOperators.length)]);
        operand.append("(");
        operand.append(rand.nextDouble()*1000);
        operand.append(")");
        return operand.toString();
    }

    public void setChance(int chance) {
        this.chance = chance;
    }
}
