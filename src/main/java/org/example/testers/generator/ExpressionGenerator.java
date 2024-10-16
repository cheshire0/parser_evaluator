package org.example.testers.generator;

import java.util.Random;
import java.util.Stack;

//todo interface-be és ne legyen static
//todo param: min max operator szám

public class ExpressionGenerator implements IExpressionGenerator{
    private final Random rand = new Random(42);
    private int minOps;
    private int maxOps;
    private int opNumber=0;

    private final String[] binaryOperators = {
            "+",
            "-",
            "*",
            //"/" expression's gotta be correct
    };

    //todo is it needed?
    private final String[] unaryOperators = {
            "sqrt",
            "sin"
    };

    //todo gyökvonás külön pl - 0-val osztás, gyök alatt negatív -> van benne hiba?
    //todo kézzel egy nagyon hosszú query és abban cserélgetek -> összehas?

    private final Stack<String> operators = new Stack<>();
    private final Stack<String> operands = new Stack<>();

    public String generate(int minOperators, int maxOperators){
        minOps = minOperators;
        maxOps = maxOperators;
        opNumber=0;
        addOperator();

        StringBuilder expression = new StringBuilder();
        while(!operators.isEmpty()){
            expression.append(operands.pop());
            expression.append(" ");
            expression.append(operators.pop());
            expression.append(" ");
        }
        expression.append(operands.pop());
        return expression.toString();
    }
//todo performance, dont care mert csak egyszer
    private void addOperator(){
        if((opNumber >= minOps && rand.nextBoolean()) || opNumber == maxOps){
            //cut branch
            operands.push(String.valueOf(rand.nextDouble()*100000));
        }
        else {
            addBinaryOperator();
        }
    }

    private void addBinaryOperator(){
        operators.push(binaryOperators[rand.nextInt(binaryOperators.length)]);
        opNumber++;
        addOperator();
        addOperator();
    }

    //todo finish
    private void addUnaryOperator(){
        operators.push(unaryOperators[rand.nextInt(unaryOperators.length)]);
        opNumber++;
        operators.push("(");
        operators.push(")");
        addOperator();
    }
}
