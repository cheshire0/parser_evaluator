package org.parser.evaluator.strategies.evalex;

import com.ezylang.evalex.Expression;
import com.ezylang.evalex.data.EvaluationValue;
import com.ezylang.evalex.operators.AbstractOperator;
import com.ezylang.evalex.operators.PostfixOperator;
import com.ezylang.evalex.parser.Token;
import static com.ezylang.evalex.operators.OperatorIfc.OPERATOR_PRECEDENCE_POWER_HIGHER;
import static org.parser.evaluator.util.test.MathTestUtil.factorial;

@PostfixOperator(precedence = OPERATOR_PRECEDENCE_POWER_HIGHER)
public class FactorialOperator extends AbstractOperator {

    @Override
    public EvaluationValue evaluate(
            Expression expression, Token operatorToken, EvaluationValue... operands) {
        return expression.convertValue(factorial(operands[0].getNumberValue().intValue()));
    }
}

