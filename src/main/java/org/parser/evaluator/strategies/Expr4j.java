package org.parser.evaluator.strategies;

import in.pratanumandal.expr4j.expression.Expression;
import in.pratanumandal.expr4j.expression.ExpressionBuilder;
import in.pratanumandal.expr4j.expression.ExpressionConfig;
import in.pratanumandal.expr4j.expression.ExpressionDictionary;
import in.pratanumandal.expr4j.token.Operator;
import in.pratanumandal.expr4j.token.OperatorType;
import tk.pratanumandal.expr4j.ExpressionEvaluator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Expr4j implements IParser{

    enum Type {
        NUMBER,
        CONDITION
    }
    class Composite {

        private final Type type;
        private Double number;
        private Boolean condition;

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
                return this.doubleValue() == this.intValue() ?
                        String.valueOf(this.intValue()) :
                        String.valueOf(this.doubleValue());
            } else if (this.getType() == Type.CONDITION) {
                return String.valueOf(this.booleanValue());
            }
            return null;
        }
    }
    protected ExpressionDictionary<Composite> expressionDictionary;
    protected ExpressionConfig<Composite> expressionConfig;
    private ExpressionBuilder<Composite> builder = new ExpressionBuilder<>(new ExpressionConfig<Composite>() {
        @Override
        protected Composite stringToOperand(String operand) {
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
            return operand.toString();
        }

        @Override
        protected List<String> getOperandPattern() {
            List<String> list = super.getOperandPattern();
            list.addAll(Arrays.asList("true", "false"));
            return list;
        }
    });

    @Override
    public Object evaluateWithoutVariables(String expression) {
        //version 0.0.3 does not support variables afaik
        ExpressionEvaluator exprEval = new ExpressionEvaluator();
        return exprEval.evaluate(expression);
    }

    @Override
    public Object evaluate(String expression) {

        //version 1.0
        try {
            expressionDictionary = builder.getExpressionDictionary();
            expressionConfig = builder.getExpressionConfig();
            expressionDictionary.addOperator(new Operator<>("+", OperatorType.INFIX, 1, (parameters) ->
                    new Composite(parameters.get(0).value().doubleValue() + parameters.get(1).value().doubleValue())));

            expressionDictionary.addOperator(new Operator<>("-", OperatorType.INFIX, 1, (parameters) ->
                    new Composite(parameters.get(0).value().doubleValue() - parameters.get(1).value().doubleValue())));

            expressionDictionary.addOperator(new Operator<>("*", OperatorType.INFIX, 2, (parameters) ->
                    new Composite(parameters.get(0).value().doubleValue() * parameters.get(1).value().doubleValue())));

            expressionDictionary.addOperator(new Operator<>("/", OperatorType.INFIX, 2, (parameters) ->
                    new Composite(parameters.get(0).value().doubleValue() / parameters.get(1).value().doubleValue())));

            expressionDictionary.addOperator(new Operator<>("^", OperatorType.INFIX, 3, (parameters) ->
                    new Composite( Math.pow(parameters.get(0).value().doubleValue(), parameters.get(1).value().doubleValue()))));

            Expression<Composite> expr = builder.build(expression);
            Composite res = expr.evaluate(variables);
            if (res.getType() == Type.NUMBER) {
                return res.doubleValue() == res.intValue() ?
                        res.intValue() :
                        res.doubleValue();
            } else if (res.getType() == Type.CONDITION) {
                return res.booleanValue();
            }
            return null;
        } catch (Exception e) {
            //version 0.0.3 does not support variables afaik
            ExpressionEvaluator exprEval = new ExpressionEvaluator();
            return exprEval.evaluate(expression);
        }
    }

    @Override
    public String toString() {
        return "Expr4j";
    }

    Map<String, Composite> variables = new HashMap<>();

    @Override
    public void setVariable(String name, Double variable){
        variables.put(name, new Composite(variable));
    }
}
