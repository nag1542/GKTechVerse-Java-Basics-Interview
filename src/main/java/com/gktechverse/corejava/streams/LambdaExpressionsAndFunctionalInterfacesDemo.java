package com.gktechverse.corejava.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 * Lambda Expressions & Functional Interfaces
 *
 * Why lambdas exist (real reason):
 * 1) To remove boilerplate when passing behavior (strategy/callback) as input.
 * 2) To make code read as business intent instead of ceremony.
 * 3) To enable fluent APIs such as Streams (filter/map/sorted) with concise behavior blocks.
 */
public class LambdaExpressionsAndFunctionalInterfacesDemo {

    @FunctionalInterface
    interface PriceRule {
        boolean isValid(int amount); // single abstract method = behavior contract
    }

    public static void main(String[] args) {
        System.out.println("\n--- Lambda Expressions & Functional Interfaces in Java ---");
        System.out.println("Problem solved by lambdas: Java methods cannot be passed directly as arguments; only objects can be passed.");
        System.out.println("So we pass a functional-interface object that carries behavior. Lambda is cleaner syntax for that object.");

        oldCodeVsLambdaSort();
        whyLambdasExistRealReason();
    }

    private static void oldCodeVsLambdaSort() {
        List<String> names = new ArrayList<>(Arrays.asList("Riya", "Aman", "Neha", "Vikram"));

        // Java 7: pass behavior via anonymous class (verbose)
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return a.compareTo(b);
            }
        });
        System.out.println("1) Java 7 anonymous class sort: " + names);

        // Java 8: same behavior via lambda (concise)
        names.sort((a, b) -> a.compareTo(b));
        System.out.println("2) Java 8 lambda sort:          " + names);

        // Java 8: method reference (most concise where applicable)
        names.sort(String::compareTo);
        System.out.println("3) Java 8 method reference sort:" + names);
    }

    private static void whyLambdasExistRealReason() {
        List<Integer> amounts = Arrays.asList(500, 1200, 1800, 2500, 900);

        // OLD STYLE: custom behavior object using anonymous class
        PriceRule oldStyleRule = new PriceRule() {
            @Override
            public boolean isValid(int amount) {
                return amount >= 1500;
            }
        };

        // NEW STYLE: same custom behavior using lambda
        PriceRule lambdaRule = amount -> amount >= 1500;

        // Built-in functional interface (Predicate) for the same use case
        Predicate<Integer> builtInRule = amount -> amount >= 1500;

        System.out.println("\nWHY LAMBDAS EXIST — THE REAL REASON");
        System.out.println("In Java, we cannot pass a method directly; we pass an object that has that behavior.");
        System.out.println("Anonymous classes and lambdas both create that object; lambda is just cleaner syntax.");

        System.out.println("\nOld style rule (anonymous class) result:");
        printValidAmounts(amounts, oldStyleRule);

        System.out.println("\nLambda rule result (same logic, less code):");
        printValidAmounts(amounts, lambdaRule);

        System.out.println("\nBuilt-in Predicate with Stream API (where lambdas shine most):");
        amounts.stream()
                .filter(builtInRule)
                .forEach(amount -> System.out.println("Approved amount: " + amount));
    }

    // Notice: this method accepts an object (PriceRule), not a method reference directly.
    // Lambda expression is compiled into an object implementing PriceRule.
    private static void printValidAmounts(List<Integer> amounts, PriceRule rule) {
        for (Integer amount : amounts) {
            if (rule.isValid(amount)) {
                System.out.println("Approved amount: " + amount);
            }
        }
    }
}
