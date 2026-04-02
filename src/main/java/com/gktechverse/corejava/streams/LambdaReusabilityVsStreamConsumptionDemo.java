package com.gktechverse.corejava.streams;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Interview Q3: Are lambdas reusable? Can you call one twice?
 */
public class LambdaReusabilityVsStreamConsumptionDemo {

    public static void main(String[] args) {
        System.out.println("\n--- Q3) Lambda reusability vs Stream single-use ---");

        List<String> names = List.of("Alexander", "Bob", "Michael", "Amy", "Jennifer");

        // Yes — lambda is just an object; reuse freely
        Predicate<String> isLong = s -> s.length() > 5;

        System.out.println("First reuse of same Predicate:");
        names.stream().filter(isLong).forEach(System.out::println);

        long count = names.stream().filter(isLong).count();
        System.out.println("Second reuse of same Predicate (count): " + count);

        // Streams are single-use: one terminal operation consumes it
        Stream<String> s = names.stream().filter(isLong);
        long streamCount = s.count();
        System.out.println("Stream count (first terminal op): " + streamCount);

        try {
            s.findAny(); // IllegalStateException: stream already consumed
        } catch (IllegalStateException ex) {
            System.out.println("Expected exception on second terminal op: " + ex.getClass().getSimpleName());
        }
    }
}
