package com.gktechverse.corejava.streams;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

/**
 * Interview Q5: What is a BiFunction? When do we use it?
 */
public class LambdaBiFunctionBiPredicateBiConsumerDemo {

    public static void main(String[] args) {
        System.out.println("\n--- Q5) BiFunction / BiPredicate / BiConsumer ---");

        // BiFunction<T,U,R> — takes TWO inputs, returns one result
        BiFunction<String, Integer, String> repeat = (s, n) -> s.repeat(n);
        System.out.println("BiFunction result: " + repeat.apply("ha", 3)); // hahaha

        // BiPredicate<T,U> — takes two inputs, returns boolean
        BiPredicate<String, Integer> longerThan = (s, n) -> s.length() > n;
        System.out.println("BiPredicate result (\"Java\", 3): " + longerThan.test("Java", 3));

        // BiConsumer<T,U> — takes two inputs, returns void
        // Common usage in Map.forEach
        BiConsumer<String, Integer> printEntry = (key, value) -> System.out.println(key + "=" + value);
        Map<String, Integer> scores = new LinkedHashMap<>();
        scores.put("Alice", 90);
        scores.put("Bob", 85);

        System.out.println("BiConsumer via Map.forEach:");
        scores.forEach(printEntry);
    }
}
