package com.gktechverse.corejava.streams;

import java.util.Arrays;
import java.util.List;

/**
 * Demonstrates lazy evaluation in streams.
 */
public class LazyEvaluationDemo {

    public static void main(String[] args) {
        System.out.println("\n2) Lazy evaluation in streams");

        List<String> employees = Arrays.asList("Asha", "Ravi", "Meera", "Arun");

        System.out.println("Building stream pipeline (nothing executes yet)...");
        var pipeline = employees.stream()
                .filter(name -> {
                    System.out.println("filter called for: " + name);
                    return name.startsWith("A") || name.startsWith("M");
                })
                .map(name -> {
                    System.out.println("map called for: " + name);
                    return name.toUpperCase();
                });

        System.out.println("No terminal operation yet, so no filter/map output above this line.");
        System.out.println("Now terminal operation starts: toList()");
        List<String> result = pipeline.toList();

        System.out.println("Lazy pipeline result: " + result);
    }
}
