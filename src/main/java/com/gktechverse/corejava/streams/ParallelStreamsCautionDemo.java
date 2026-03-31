package com.gktechverse.corejava.streams;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Pattern demo: parallel streams and when to use them carefully.
 */
public class ParallelStreamsCautionDemo {

    public static void main(String[] args) {
        System.out.println("\n6) Parallel streams (use with caution)");

        List<String> names = Arrays.asList(
                "Aarav", "Mira", "Kabir", "Isha", "Rohan", "Anaya", "Dev", "Nisha"
        );

        List<String> result = names.parallelStream()
                .filter(ParallelStreamsCautionDemo::expensiveCheck)
                .collect(Collectors.toList());

        System.out.println("Input size: " + names.size());
        System.out.println("Filtered result (parallelStream): " + result);

        System.out.println("WARNING - avoid parallelStream for:");
        System.out.println("- Small lists (threading overhead may be higher than benefit)");
        System.out.println("- Stateful operations (sorting/distinct heavy pipelines)");
        System.out.println("- I/O operations (DB/API/network calls)");

        System.out.println("Use parallelStream mainly for CPU-intensive work on large datasets (10k+ elements).");
    }

    private static boolean expensiveCheck(String name) {
        long score = 0;
        for (int i = 0; i < 50_000; i++) {
            score += (long) name.charAt(i % name.length()) * (i + 1);
        }
        return score % 2 == 0;
    }
}
