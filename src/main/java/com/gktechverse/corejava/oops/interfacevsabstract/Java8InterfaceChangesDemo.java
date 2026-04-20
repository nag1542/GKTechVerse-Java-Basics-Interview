package com.gktechverse.corejava.oops.interfacevsabstract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Interview focus: Java 8 interface changes.
 * Demonstrates default and static methods in interfaces.
 */
public class Java8InterfaceChangesDemo {

    interface LearningCollection<E> {
        boolean add(E element);

        boolean remove(Object o);

        default boolean addAll(Iterable<? extends E> source) {
            boolean modified = false;
            for (E e : source) {
                if (add(e)) {
                    modified = true;
                }
            }
            return modified;
        }

        static <E> List<E> empty() {
            return Collections.emptyList();
        }
    }

    static class SimpleCollection<E> implements LearningCollection<E> {
        private final List<E> data = new ArrayList<>();

        @Override
        public boolean add(E element) {
            return data.add(element);
        }

        @Override
        public boolean remove(Object o) {
            return data.remove(o);
        }

        public List<E> snapshot() {
            return new ArrayList<>(data);
        }
    }

    public static void main(String[] args) {
        SimpleCollection<String> topics = new SimpleCollection<>();

        topics.add("Encapsulation");
        topics.addAll(List.of("Abstraction", "Polymorphism"));

        System.out.println("=== Java 8 Interface Changes Demo ===");
        System.out.println("Topics: " + topics.snapshot());
        System.out.println("Static factory from interface: " + LearningCollection.<String>empty());
    }
}
