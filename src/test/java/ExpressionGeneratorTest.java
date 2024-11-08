import org.parser.evaluator.testers.generator.ExpressionGenerator;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertTrue;

public class ExpressionGeneratorTest {

    private final Random rand = new Random(42);
    private final ExpressionGenerator gen = new ExpressionGenerator();

    @Test
    public void testOperatorNumber() {
        for(int i=0; i<10; i++){
            int lowerBound = i;
            int upperBound = i+rand.nextInt(10);
            String expr = gen.generate(lowerBound,upperBound);
            int countSpecial = expr.replaceAll("[A-Za-z0-9\\s.]", "").length();

            assertTrue("Number is not within the expected range, expression: "+expr+" lower bound: "+lowerBound+" upper: "+upperBound+" opCount: "+countSpecial,
                    countSpecial >= lowerBound && countSpecial <= upperBound);
        }
    }
}
