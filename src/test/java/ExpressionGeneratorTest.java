import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.parser.evaluator.testers.generator.ExpressionGenerator;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ExpressionGeneratorTest {

    private ExpressionGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new ExpressionGenerator();
    }

    @Test
    void testGenerateExpressionWithOperators() {
        int opNumber = 5;
        String expression = generator.generate(opNumber);

        // Verify that the expression contains at least `opNumber` operators
        int operatorCount = countOccurrences(expression, "+", "-", "*");
        assertEquals(opNumber, operatorCount, "Expression should contain the correct number of binary operators");

        // Verify that the expression starts with a number
        assertTrue(expression.matches("^\\d+.*"), "Expression should start with a number");
    }

    @Test
    void testUnaryOperatorInExpression() {
        generator.setChance(0); // Force all operands to be unary operators
        String expression = generator.generate(3);

        // Verify that unary operators are present in the expression
        assertTrue(expression.contains("cos(") || expression.contains("sin("), "Expression should contain unary operators");
    }

    @Test
    void testUnaryOperatorFormat() {
        String unaryOperator = generator.addUnaryOperator();

        // Verify that the unary operator is correctly formatted
        assertTrue(unaryOperator.matches("(cos|sin)\\(\\d+\\.\\d+\\)"), "Unary operator format is incorrect");
    }

    @Test
    void testOperandWithChance100() {
        generator.setChance(100); // Ensure operands are always numbers
        String operand = generator.addOperand();

        // Verify that the operand is a number
        assertTrue(operand.matches("\\d+\\.\\d+"), "Operand should be a number when chance is 100%");
    }

    @Test
    void testOperandWithChance0() {
        generator.setChance(0); // Ensure operands are always unary operators
        String operand = generator.addOperand();

        // Verify that the operand is a unary operator
        assertTrue(operand.matches("(cos|sin)\\(\\d+\\.\\d+\\)"), "Operand should be a unary operator when chance is 0%");
    }

    @Test
    void testSetChance() {
        generator.setChance(70);
        assertEquals(70, generator.chance, "Chance should be updated correctly");

        generator.setChance(30);
        assertEquals(30, generator.chance, "Chance should be updated correctly");
    }

    // Utility method to count occurrences of multiple substrings in a string
    private int countOccurrences(String text, String... substrings) {
        int count = 0;
        for (String substring : substrings) {
            count += text.split(Pattern.quote(substring), -1).length - 1;
        }
        return count;
    }
}
