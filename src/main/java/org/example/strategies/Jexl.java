package org.example.strategies;

import org.apache.commons.jexl3.*;

public class Jexl implements IParser{
    @Override
    public String evaluate(String expression) {
        JexlEngine jexl = new JexlBuilder().create();
        JexlExpression e = jexl.createExpression(expression);
        JexlContext context = new MapContext();

        return e.evaluate(context).toString();
    }

    @Override
    public void addSource(Object source) {

    }
}
