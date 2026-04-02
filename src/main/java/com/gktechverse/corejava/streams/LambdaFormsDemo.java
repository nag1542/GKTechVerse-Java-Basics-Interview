package com.gktechverse.corejava.streams;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Demonstrates the most common lambda forms used in Java interviews.
 */
public class LambdaFormsDemo {

    public static void main(String[] args) {
        System.out.println("\n--- Different Types of Lambda Forms ---");

        // Form 1 — No parameters
        Runnable r = () -> System.out.println("running");
        r.run();

        // Form 2 — One parameter — parentheses optional
        Consumer<String> print = s -> System.out.println(s);
        print.accept("hello from one-parameter lambda");

        // Form 3 — Multiple parameters — parentheses required
        Comparator<Integer> cmp = (a, b) -> a - b;
        System.out.println("Comparator compare(10, 20): " + cmp.compare(10, 20));

        // Form 4 — Multi-line body — braces and explicit return required
        Function<String, Integer> parse = (s) -> {
            if (s == null) return 0;
            return Integer.parseInt(s);
        };
        System.out.println("parse(null): " + parse.apply(null));
        System.out.println("parse(\"42\"): " + parse.apply("42"));

        // Form 5 — Method reference — shortest form
        Consumer<String> print2 = System.out::println;      // instance::method
        Function<String, Integer> len = String::length;     // type::instance method
        Supplier<List<String>> make = ArrayList::new;       // constructor reference

        print2.accept("method reference print");
        System.out.println("length of 'lambda': " + len.apply("lambda"));
        List<String> created = make.get();
        created.add("created via constructor reference");
        print2.accept(created.get(0));
    }
}
