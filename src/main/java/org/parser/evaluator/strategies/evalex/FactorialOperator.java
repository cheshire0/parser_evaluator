package org.parser.evaluator.strategies.evalex;

import com.ezylang.evalex.Expression;
import com.ezylang.evalex.data.EvaluationValue;
import com.ezylang.evalex.operators.AbstractOperator;
import com.ezylang.evalex.operators.PostfixOperator;
import com.ezylang.evalex.parser.Token;

import static com.ezylang.evalex.operators.OperatorIfc.OPERATOR_PRECEDENCE_POWER_HIGHER;
import static org.parser.evaluator.util.test.MathTestUtil.factorial;

/**
 * FactorialOperator is a custom postfix operator implementation for EvalEx.
 * It calculates the factorial of a numeric operand preceding the `!` symbol.
 */
@PostfixOperator(precedence = OPERATOR_PRECEDENCE_POWER_HIGHER) // Sets the operator precedence higher than exponentiation.
public class FactorialOperator extends AbstractOperator {

    /**
     * Evaluates the factorial of the operand.
     *
     * @param expression     The current expression being evaluated.
     * @param operatorToken  The token representing the `!` operator in the expression.
     * @param operands       The operands passed to the operator. This operator expects a single operand.
     * @return An EvaluationValue containing the computed factorial result.
     */
    @Override
    public EvaluationValue evaluate(
            Expression expression, Token operatorToken, EvaluationValue... operands) {

        // Validate that exactly one operand is provided for this postfix operator.
        if (operands.length != 1) {
            throw new IllegalArgumentException("Factorial operator requires exactly one operand.");
        }

        // Retrieve the operand value and calculate its factorial using an external utility method.
        int operandValue = operands[0].getNumberValue().intValue();
        double result = factorial(operandValue);

        // Convert the result into an EvaluationValue and return it.
        return expression.convertValue(result);
    }
}
