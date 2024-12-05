package org.parser.evaluator.util.test;

import java.math.BigDecimal;

public class MathTestUtil {
    // Define a small epsilon value to determine floating-point equality tolerance
    private static final double EPSILON = 1e-9;

    /**
     * Compares two floating-point values and checks if they are close enough.
     * This method uses a small epsilon value to determine if the two numbers are nearly equal.
     *
     * @param expected The expected value.
     * @param actual The actual value to compare.
     * @return true if the absolute difference between the two values is smaller than EPSILON.
     */
    public static boolean areCloseEnough(double expected, double actual) {
        return Math.abs(expected - actual) < EPSILON;  // Check if the absolute difference is less than epsilon
    }

    /**
     * Converts various types of objects to a double value.
     * This method can handle objects of type Double, Integer, Float, BigDecimal, and String.
     *
     * @param obj The object to convert.
     * @return The corresponding double value.
     * @throws IllegalArgumentException If the object cannot be converted to a double.
     */
    public static double toDouble(Object obj) {
        if (obj instanceof Double) {
            return (Double) obj;  // If the object is a Double, return it directly
        } else if (obj instanceof Integer) {
            return ((Integer) obj).doubleValue();  // If the object is an Integer, convert to double
        } else if (obj instanceof Float) {
            return ((Float) obj).doubleValue();  // If the object is a Float, convert to double
        } else if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).doubleValue();  // If the object is a BigDecimal, convert to double
        } else if (obj instanceof String) {
            try {
                return Double.parseDouble((String) obj);  // If the object is a String, try to parse it as a double
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Cannot convert String to double: " + obj);  // If parsing fails, throw an exception
            }
        } else {
            // If the object is of any unsupported type, throw an exception
            throw new IllegalArgumentException("Unsupported type for conversion to double: " + obj.getClass().getName());
        }
    }

    /**
     * Calculates the factorial of a number.
     * This method computes the factorial of a non-negative integer.
     * The factorial of n is defined as n! = n * (n-1) * (n-2) * ... * 1.
     *
     * @param n The number to calculate the factorial of.
     * @return The factorial of n as a long value.
     * @throws IllegalArgumentException If n is negative.
     */
    public static long factorial(double n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is undefined for negative numbers: " + n);
        }

        long fact = 1;
        for (int i = 2; i <= n; i++) {  // Loop from 2 to n (inclusive) to calculate the factorial
            fact = fact * i;
        }
        return fact;
    }
}
