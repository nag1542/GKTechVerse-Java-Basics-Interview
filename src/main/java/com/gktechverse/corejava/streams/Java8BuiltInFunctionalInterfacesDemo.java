package com.gktechverse.corejava.streams;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Demonstrates Java 8 built-in functional interfaces and common composition patterns.
 */
public class Java8BuiltInFunctionalInterfacesDemo {

    public static void main(String[] args) {
        System.out.println("\n--- Java 8 Built-in Functional Interfaces Demo ---");

        predicateDemo();
        functionDemo();
        consumerDemo();
        supplierDemo();
    }

    private static void predicateDemo() {
        Predicate<String> startsWithA = s -> s.startsWith("A");
        Predicate<String> longerThan3 = s -> s.length() > 3;

        // Compose predicates with .and() .or() .negate()
        Predicate<String> both = startsWithA.and(longerThan3);

        List<String> names = List.of("Alice", "Bob", "Anna", "Amy", "Alexander");

        System.out.println("\nPredicate.and() output (startsWithA AND longerThan3):");
        names.stream().filter(both).forEach(System.out::println); // Alice, Anna, Alexander

        System.out.println("Predicate.negate() output (NOT startsWithA):");
        names.stream().filter(startsWithA.negate()).forEach(System.out::println);

        System.out.println("Predicate.or() output (startsWithA OR longerThan3):");
        names.stream().filter(startsWithA.or(longerThan3)).forEach(System.out::println);
    }

    private static void functionDemo() {
        Function<String, Integer> toLength = String::length;
        Function<Integer, String> toMessage = i -> "Length: " + i;

        // andThen — chain: toLength first, then toMessage
        Function<String, String> pipeline = toLength.andThen(toMessage);
        System.out.println("\nFunction.andThen() output: " + pipeline.apply("Hello"));

        // compose example: apply trim first, then toLength
        Function<String, Integer> lengthAfterTrim = toLength.compose(String::trim);
        System.out.println("Function.compose() output: " + lengthAfterTrim.apply("  Java  "));

        System.out.println("compose rule: f.compose(g) => g first, then f");
        System.out.println("andThen rule: f.andThen(g) => f first, then g");
    }

    private static void consumerDemo() {
        Consumer<String> log = s -> System.out.println("[LOG] " + s);
        Consumer<String> audit = s -> System.out.println("[AUDIT] " + s);

        // andThen — both consumers run in sequence
        Consumer<String> logAndAudit = log.andThen(audit);
        System.out.println("\nConsumer.andThen() output:");
        logAndAudit.accept("user.login");

        List<String> names = List.of("Alice", "Bob", "Anna", "Amy", "Alexander");
        System.out.println("Consumer in streams:");
        names.stream().forEach(log);

        System.out.println("Consumer with Optional.ifPresent:");
        Optional.of("Alice").ifPresent(log);
    }

    private static void supplierDemo() {
        Supplier<List<String>> listMaker = ArrayList::new;
        Supplier<LocalDate> today = LocalDate::now;

        System.out.println("\nSupplier examples:");
        System.out.println("New list from Supplier: " + listMaker.get().getClass().getSimpleName());
        System.out.println("Today's date from Supplier: " + today.get());

        // Most important use — lazy default in Optional
        String r1 = Optional.of("found").orElse(expensiveCall()); // eager
        String r2 = Optional.of("found").orElseGet(Java8BuiltInFunctionalInterfacesDemo::expensiveCall); // lazy

        System.out.println("orElse result: " + r1);
        System.out.println("orElseGet result: " + r2);

        List<String> names = List.of("Alice", "Bob", "Anna", "Amy", "Alexander");
        LinkedList<String> linked = names.stream().collect(Collectors.toCollection(LinkedList::new));
        System.out.println("Collectors.toCollection with Supplier: " + linked.getClass().getSimpleName());
    }

    private static String expensiveCall() {
        System.out.println("expensiveCall() executed");
        return "fallback";
    }
}
