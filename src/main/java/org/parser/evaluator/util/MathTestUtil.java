package org.parser.evaluator.util;

import java.math.BigDecimal;

public class MathTestUtil {
    private static final double EPSILON = 1e-9;

    public static boolean areCloseEnough(double expected, double actual) {
        return Math.abs(expected - actual) < EPSILON;
    }

    public static double toDouble(Object obj) {
        if (obj instanceof Double) {
            return (Double) obj;
        } else if (obj instanceof Integer) {
            return ((Integer) obj).doubleValue();
        } else if (obj instanceof Float) {
            return ((Float) obj).doubleValue();
        } else if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).doubleValue();
        } else if (obj instanceof String) {
            try {
                return Double.parseDouble((String) obj);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Cannot convert String to double: " + obj);
            }
        } else {
            throw new IllegalArgumentException("Unsupported type for conversion to double: " + obj.getClass().getName());
        }
    }

    public static long factorial(double n) {
        long fact = 1;
        for (int i = 2; i < n+1; i++) {
            fact = fact * i;
        }
        return fact;
    }
}

