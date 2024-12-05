package org.parser.evaluator.strategies;

import in.pratanumandal.expr4j.expression.*;
import in.pratanumandal.expr4j.token.Function;
import in.pratanumandal.expr4j.token.Operator;
import in.pratanumandal.expr4j.token.OperatorType;
import tk.pratanumandal.expr4j.ExpressionEvaluator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.parser.evaluator.util.test.MathTestUtil.factorial;

/**
 * The Expr4j class is an implementation of the IParser interface
 * that leverages the Expr4j library to evaluate mathematical expressions.
 * It supports variables, custom functions, and custom operators.
 */
public class Expr4j implements IParser {

    /** Enum representing the type of a Composite (NUMBER or CONDITION). */
    enum Type {
        NUMBER,     // Represents a numeric value.
        CONDITION   // Represents a boolean value (true/false).
    }

    /**
     * Composite class encapsulates both numeric and boolean values
     * and provides utility methods to retrieve and convert them.
     */
    class Composite {
        private final Type type;   // Type of the composite (NUMBER or CONDITION).
        private Double number;     // Numeric value, if the type is NUMBER.
        private Boolean condition; // Boolean value, if the type is CONDITION.

        // Constructors for initializing numeric or boolean values.
        public Composite(Integer number) {
            this.type = Type.NUMBER;
            this.number = number.doubleValue();
        }

        public Composite(Double number) {
            this.type = Type.NUMBER;
            this.number = number;
        }

        public Composite(Boolean condition) {
            this.type = Type.CONDITION;
            this.condition = condition;
        }

        // Getters for the type and value of the composite.
        public Type getType() {
            return type;
        }

        public int intValue() {
            return number.intValue();
        }

        public double doubleValue() {
            return number;
        }

        public Boolean booleanValue() {
            return condition;
        }

        @Override
        public String toString() {
            if (this.getType() == Type.NUMBER) {
                // Return integer representation if the value is whole.
                return this.doubleValue() == this.intValue() ?
                        String.valueOf(this.intValue()) :
                        String.valueOf(this.doubleValue());
            } else if (this.getType() == Type.CONDITION) {
                return String.valueOf(this.booleanValue());
            }
            return null;
        }
    }

    // Expression dictionary and configuration for customizing functions and operators.
    private ExpressionDictionary<Composite> expressionDictionary;
    private ExpressionConfig<Composite> expressionConfig;

    // A builder for creating expressions with the configured dictionary and rules.
    private ExpressionBuilder<Composite> builder = new ExpressionBuilder<>(new ExpressionConfig<Composite>() {
        @Override
        protected Composite stringToOperand(String operand) {
            // Convert string representation to Composite type.
            if (operand.equals("true")) {
                return new Composite(true);
            } else if (operand.equals("false")) {
                return new Composite(false);
            } else {
                return new Composite(Double.parseDouble(operand));
            }
        }

        @Override
        protected String operandToString(Composite operand) {
            // Convert Composite type to its string representation.
            return operand.toString();
        }

        @Override
        protected List<String> getOperandPattern() {
            // Add boolean values to the recognized operand patterns.
            List<String> list = super.getOperandPattern();
            list.addAll(Arrays.asList("true", "false"));
            return list;
        }
    });

    // Variables for use in expressions.
    private Map<String, Composite> variables = new HashMap<>();

    /**
     * Constructor initializes custom operators and functions in the dictionary.
     */
    public Expr4j() {
        expressionDictionary = builder.getExpressionDictionary();
        expressionConfig = builder.getExpressionConfig();

        // Add standard operators.
        expressionDictionary.addOperator(new Operator<>("+", OperatorType.INFIX, 1, (parameters) ->
                new Composite(parameters.get(0).value().doubleValue() + parameters.get(1).value().doubleValue())));

        expressionDictionary.addOperator(new Operator<>("-", OperatorType.INFIX, 1, (parameters) ->
                new Composite(parameters.get(0).value().doubleValue() - parameters.get(1).value().doubleValue())));

        expressionDictionary.addOperator(new Operator<>("*", OperatorType.INFIX, 2, (parameters) ->
                new Composite(parameters.get(0).value().doubleValue() * parameters.get(1).value().doubleValue())));

        expressionDictionary.addOperator(new Operator<>("/", OperatorType.INFIX, 2, (parameters) ->
                new Composite(parameters.get(0).value().doubleValue() / parameters.get(1).value().doubleValue())));

        expressionDictionary.addOperator(new Operator<>("^", OperatorType.INFIX, 3, (parameters) ->
                new Composite(Math.pow(parameters.get(0).value().doubleValue(), parameters.get(1).value().doubleValue()))));
    }

    /**
     * Evaluates an expression without variables.
     *
     * @param expression the mathematical expression.
     * @return the result of the evaluation.
     */
    @Override
    public Object evaluateWithoutVariables(String expression) {
        // Use a simplified evaluator since variables are not supported in some versions.
        ExpressionEvaluator exprEval = new ExpressionEvaluator();
        return exprEval.evaluate(expression);
    }

    /**
     * Evaluates an expression with custom functions and operators.
     *
     * @param expression the mathematical expression.
     * @return the result of the evaluation.
     */
    @Override
    public Object evaluateWithCustomFunc(String expression) {
        // Add a custom "factorial" function.
        Function<Composite> fact = new Function<>("factorial",
                list -> new Composite(Double.valueOf(factorial(list.get(0).value().intValue())))
        );

        // Add a custom "!" operator for factorial.
        expressionDictionary.addFunction(fact);
        expressionDictionary.addOperator(new Operator<>("!", OperatorType.POSTFIX, 4, (parameters) ->
                new Composite(Double.valueOf(factorial(parameters.get(0).value().intValue())))));

        // Build and evaluate the expression.
        Expression<Composite> expr = builder.build(expression);
        Composite res = expr.evaluate();
        return getCompositeValue(res);
    }

    /**
     * Evaluates an expression, attempting to use variables if supported.
     *
     * @param expression the mathematical expression.
     * @return the result of the evaluation.
     */
    @Override
    public Object evaluate(String expression) {
        // Version 1.0
        try {
            // Attempt to evaluate using variables.
            Expression<Composite> expr = builder.build(expression);
            Composite res = expr.evaluate(variables);
            return getCompositeValue(res);
        } catch (Exception e) {
            // Version 0.0.3 does not support variables afaik
            // Fall back to simple evaluation if variables are unsupported.
            ExpressionEvaluator exprEval = new ExpressionEvaluator();
            return exprEval.evaluate(expression);
        }
    }

    /**
     * Sets a variable for use in expressions.
     *
     * @param name      the name of the variable.
     * @param variable  the value of the variable.
     */
    @Override
    public void setVariable(String name, Double variable) {
        variables.put(name, new Composite(variable));
    }

    /**
     * Converts a Composite value to its appropriate Java representation.
     *
     * @param res the Composite result.
     * @return the equivalent Java value.
     */
    private Object getCompositeValue(Composite res) {
        if (res.getType() == Type.NUMBER) {
            return res.doubleValue() == res.intValue() ?
                    res.intValue() :
                    res.doubleValue();
        } else if (res.getType() == Type.CONDITION) {
            return res.booleanValue();
        }
        return null;
    }

    @Override
    public String toString() {
        return "Expr4j";
    }
}
