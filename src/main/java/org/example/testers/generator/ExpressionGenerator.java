package org.example.testers.generator;

import java.util.Random;
import java.util.Stack;

//todo interface-be és ne legyen static
//todo param: min max operator szám

public class ExpressionGenerator implements IExpressionGenerator{
    private final Random rand = new Random();
    private final int maxDepth = 10;

    private final String[] operatorStrings = {
            "+",
            "-",
            "*",
            "/"
    };

    //todo gyökvonás külön pl - 0-val osztás, gyök alatt negatív -> van benne hiba?
    //todo kézzel egy nagyon hosszú query és abban cserélgetek -> összehas?

    private final Stack<String> operators = new Stack<>();
    private final Stack<String> operands = new Stack<>();

    public String generate(int minOperators, int maxOperators){
        addOperator(0);

        StringBuilder expression = new StringBuilder();
        while(!operators.isEmpty()){
            expression.append(operands.pop());
            expression.append(operators.pop());
        }
        expression.append(operands.pop());
        return expression.toString();
    }
//todo performance, dont care mert csak egyszer
    private void addOperator(int depth){
        if(depth == maxDepth){
            operands.push(String.valueOf(rand.nextDouble()*100000));
        }
        else {
            operators.push(operatorStrings[rand.nextInt(operatorStrings.length)]);
            addOperator(depth + 1);
            addOperator(depth + 1);
        }
    }
}
