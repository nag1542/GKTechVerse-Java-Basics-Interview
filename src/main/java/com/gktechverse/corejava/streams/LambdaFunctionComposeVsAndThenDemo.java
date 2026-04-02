package com.gktechverse.corejava.streams;

import java.util.function.Function;

/**
 * Interview Q2: Difference between andThen and compose on Function.
 */
public class LambdaFunctionComposeVsAndThenDemo {

    public static void main(String[] args) {
        System.out.println("\n--- Q2) Function.andThen() vs Function.compose() ---");

        Function<Integer, Integer> doubleIt = n -> n * 2;
        Function<Integer, Integer> addTen = n -> n + 10;

        // andThen — left to right: doubleIt first, then addTen
        int andThenResult = doubleIt.andThen(addTen).apply(5); // (5*2)+10 = 20

        // compose — right to left: addTen first, then doubleIt
        int composeResult = doubleIt.compose(addTen).apply(5); // (5+10)*2 = 30

        System.out.println("andThen result: " + andThenResult);
        System.out.println("compose result: " + composeResult);
    }
}
