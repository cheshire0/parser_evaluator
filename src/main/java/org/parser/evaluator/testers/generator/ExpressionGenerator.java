package org.parser.evaluator.testers.generator;

import java.util.Random;

public class ExpressionGenerator implements IExpressionGenerator {
    private final Random rand = new Random(42);  // Random object to generate random numbers
    public int chance = 60;  // Default chance to choose between a number or a unary operator

    // List of binary operators supported
    private final String[] binaryOperators = {
            "+",
            "-",
            "*",
            // "/" is excluded to ensure division expressions are valid
    };

    // List of unary operators supported
    private final String[] unaryOperators = {
            "cos",
            "sin"
    };

    /**
     * Generate an expression with the specified number of binary operations.
     *
     * @param opNumber The number of binary operators to include in the generated expression.
     * @return A randomly generated mathematical expression.
     */
    public String generate(int opNumber) {
        StringBuilder expression = new StringBuilder();

        // Start with a random number
        expression.append(rand.nextDouble() * 1000);

        // Add the specified number of binary operators and operands
        for (int i = 0; i < opNumber; i++) {
            expression.append(addBinaryOperator());
        }

        return expression.toString();  // Return the generated expression
    }

    /**
     * Adds a random operand (either a number or a unary operation).
     *
     * @return A randomly generated operand (number or unary operation).
     */
    public String addOperand() {
        if (rand.nextInt(100) < chance) {
            // If chance is met, generate a random number operand
            return String.valueOf(rand.nextDouble() * 1000);
        } else {
            // Otherwise, generate a unary operation
            return addUnaryOperator();
        }
    }

    /**
     * Adds a random binary operator and a corresponding operand.
     *
     * @return A string containing a binary operator followed by a random operand.
     */
    public String addBinaryOperator() {
        StringBuilder operand = new StringBuilder();

        // Choose a random binary operator and append it
        operand.append(" ");
        operand.append(binaryOperators[rand.nextInt(binaryOperators.length)]);
        operand.append(" ");

        // Add the operand (number or unary operation)
        operand.append(addOperand());
        return operand.toString();
    }

    /**
     * Adds a random unary operator followed by a random operand.
     *
     * @return A string containing a unary operator with a randomly generated operand.
     */
    public String addUnaryOperator() {
        StringBuilder operand = new StringBuilder();

        // Choose a random unary operator and append it with a random operand
        operand.append(unaryOperators[rand.nextInt(unaryOperators.length)]);
        operand.append("(");
        operand.append(rand.nextDouble() * 1000);  // Random number for the operand
        operand.append(")");
        return operand.toString();
    }

    /**
     * Sets the chance percentage to choose between an operand being a number or a unary operator.
     *
     * @param chance The chance percentage (0-100) for selecting a number over a unary operator.
     */
    public void setChance(int chance) {
        this.chance = chance;  // Set the chance to the provided value
    }
}
