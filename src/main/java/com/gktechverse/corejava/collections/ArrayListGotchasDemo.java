package com.gktechverse.corejava.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Interview-focused gotchas developers commonly hit with ArrayList.
 */
public class ArrayListGotchasDemo {

    public static void main(String[] args) {
        System.out.println("=== 4 ArrayList Gotchas ===");
        gotchaOneArraysAsListFixedSize();
        gotchaTwoAutoboxingCost();
        gotchaThreeSubListIsAView();
        gotchaFourRemoveInForEachFailFast();
    }

    private static void gotchaOneArraysAsListFixedSize() {
        System.out.println("\nGotcha 1 - Arrays.asList() returns fixed-size list");
        List<String> fixed = Arrays.asList("Alice", "Bob", "Carol");

        try {
            fixed.add("Dave");
        } catch (UnsupportedOperationException ex) {
            System.out.println("add() on Arrays.asList(...) failed as expected: " + ex.getClass().getSimpleName());
        }

        fixed.set(1, "Bobby");
        System.out.println("set() works on fixed-size list: " + fixed);

        List<String> mutable = new ArrayList<>(Arrays.asList("Alice", "Bob"));
        mutable.add("Dave");
        System.out.println("Mutable version via new ArrayList<>(Arrays.asList(...)): " + mutable);

        List<String> immutable = List.of("Alice", "Bob");
        System.out.println("List.of(...) gives explicit immutable list: " + immutable);
    }

    private static void gotchaTwoAutoboxingCost() {
        System.out.println("\nGotcha 2 - Autoboxing overhead with ArrayList<Integer>");
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 1_000_000; i++) {
            numbers.add(i); // int -> Integer
        }

        System.out.println("Created 1,000,000 Integer wrappers using ArrayList<Integer>.");
        System.out.println("Interview note: wrappers add memory overhead and pointer indirection.");
        System.out.println("For huge numeric workloads, prefer primitive collections libraries (e.g., Eclipse Collections IntList). ");
        System.out.println("Sample size check: " + numbers.size());
    }

    private static void gotchaThreeSubListIsAView() {
        System.out.println("\nGotcha 3 - subList() is a view, not an independent copy");
        List<String> original = new ArrayList<>(Arrays.asList("p1", "p2", "p3", "p4"));
        List<String> window = original.subList(1, 3); // p2, p3
        window.set(0, "p2-updated");

        System.out.println("subList change reflects in original: " + original);

        List<String> independentCopy = new ArrayList<>(original.subList(1, 3));
        independentCopy.set(0, "copy-only");
        System.out.println("Independent copy does not mutate original: " + original);
        System.out.println("Independent copy: " + independentCopy);
    }

    private static void gotchaFourRemoveInForEachFailFast() {
        System.out.println("\nGotcha 4 - Removing inside foreach is fail-fast");
        List<String> names = new ArrayList<>(Arrays.asList("A", "B", "C"));

        try {
            for (String name : names) {
                if ("B".equals(name)) {
                    names.remove(name); // fail-fast behavior
                }
            }
        } catch (Exception ex) {
            System.out.println("Expected fail-fast exception: " + ex.getClass().getSimpleName());
            System.out.println("Reason: structural modification during iteration triggers ConcurrentModificationException.");
        }

        List<String> safeWithRemoveIf = new ArrayList<>(Arrays.asList("A", "B", "C"));
        safeWithRemoveIf.removeIf("B"::equals);
        System.out.println("Safe solution using removeIf(...): " + safeWithRemoveIf);

        List<String> safeWithIterator = new ArrayList<>(Arrays.asList("A", "B", "C"));
        var iterator = safeWithIterator.iterator();
        while (iterator.hasNext()) {
            if ("B".equals(iterator.next())) {
                iterator.remove();
            }
        }
        System.out.println("Safe solution using Iterator.remove(): " + safeWithIterator);
    }


}
