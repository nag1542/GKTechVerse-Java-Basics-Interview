package com.gktechverse.corejava.streams;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Top 20 commonly asked stream coding problems for interview practice.
 */
public class StreamInterviewPracticeTop20 {

    public static void main(String[] args) {
        System.out.println("\n7) Interview practice: Top 20 Stream coding problems");

        List<Integer> numbers = Arrays.asList(5, 3, 9, 1, 3, 8, 2, 10, 5, 7);
        List<String> words = Arrays.asList("java", "stream", "api", "code", "java", "interview");

        problem01EvenNumbers(numbers);
        problem02OddNumbers(numbers);
        problem03Squares(numbers);
        problem04Unique(numbers);
        problem05SortedAsc(numbers);
        problem06SortedDesc(numbers);
        problem07Count(numbers);
        problem08Sum(numbers);
        problem09Average(numbers);
        problem10Max(numbers);
        problem11Min(numbers);
        problem12SecondHighest(numbers);
        problem13StartsWithDigit(numbers, 1);
        problem14JoinStrings(words);
        problem15Frequency(words);
        problem16FirstNonRepeated("swiss");
        problem17FirstRepeated("programming");
        problem18PartitionEvenOdd(numbers);
        problem19Top3(numbers);
        problem20Skip3Take4(numbers);
    }

    private static void problem01EvenNumbers(List<Integer> numbers) {
        List<Integer> result = numbers.stream().filter(n -> n % 2 == 0).toList();
        print(1, "Find all even numbers", result);
    }

    private static void problem02OddNumbers(List<Integer> numbers) {
        List<Integer> result = numbers.stream().filter(n -> n % 2 != 0).toList();
        print(2, "Find all odd numbers", result);
    }

    private static void problem03Squares(List<Integer> numbers) {
        List<Integer> result = numbers.stream().map(n -> n * n).toList();
        print(3, "Square each number", result);
    }

    private static void problem04Unique(List<Integer> numbers) {
        List<Integer> result = numbers.stream().distinct().toList();
        print(4, "Remove duplicates", result);
    }

    private static void problem05SortedAsc(List<Integer> numbers) {
        List<Integer> result = numbers.stream().sorted().toList();
        print(5, "Sort ascending", result);
    }

    private static void problem06SortedDesc(List<Integer> numbers) {
        List<Integer> result = numbers.stream().sorted(Comparator.reverseOrder()).toList();
        print(6, "Sort descending", result);
    }

    private static void problem07Count(List<Integer> numbers) {
        long result = numbers.stream().count();
        print(7, "Count elements", result);
    }

    private static void problem08Sum(List<Integer> numbers) {
        int result = numbers.stream().mapToInt(Integer::intValue).sum();
        print(8, "Sum of elements", result);
    }

    private static void problem09Average(List<Integer> numbers) {
        double result = numbers.stream().mapToInt(Integer::intValue).average().orElse(0);
        print(9, "Average of elements", result);
    }

    private static void problem10Max(List<Integer> numbers) {
        int result = numbers.stream().max(Integer::compareTo).orElseThrow();
        print(10, "Find max", result);
    }

    private static void problem11Min(List<Integer> numbers) {
        int result = numbers.stream().min(Integer::compareTo).orElseThrow();
        print(11, "Find min", result);
    }

    private static void problem12SecondHighest(List<Integer> numbers) {
        int result = numbers.stream().distinct().sorted(Comparator.reverseOrder()).skip(1).findFirst().orElseThrow();
        print(12, "Find second highest", result);
    }

    private static void problem13StartsWithDigit(List<Integer> numbers, int digit) {
        List<Integer> result = numbers.stream()
                .filter(n -> String.valueOf(n).startsWith(String.valueOf(digit)))
                .toList();
        print(13, "Numbers starting with digit " + digit, result);
    }

    private static void problem14JoinStrings(List<String> words) {
        String result = words.stream().collect(Collectors.joining(", "));
        print(14, "Join strings with comma", result);
    }

    private static void problem15Frequency(List<String> words) {
        Map<String, Long> result = words.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        print(15, "Frequency of each word", result);
    }

    private static void problem16FirstNonRepeated(String text) {
        Optional<Character> result = text.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.counting()))
                .entrySet().stream()
                .filter(entry -> entry.getValue() == 1)
                .map(Map.Entry::getKey)
                .findFirst();
        print(16, "First non-repeated char in '" + text + "'", result.orElse(null));
    }

    private static void problem17FirstRepeated(String text) {
        Set<Character> seen = new java.util.HashSet<>();
        Character result = text.chars()
                .mapToObj(c -> (char) c)
                .filter(ch -> !seen.add(ch))
                .findFirst()
                .orElse(null);
        print(17, "First repeated char in '" + text + "'", result);
    }

    private static void problem18PartitionEvenOdd(List<Integer> numbers) {
        Map<Boolean, List<Integer>> result = numbers.stream()
                .collect(Collectors.partitioningBy(n -> n % 2 == 0));
        print(18, "Partition by even/odd", result);
    }

    private static void problem19Top3(List<Integer> numbers) {
        List<Integer> result = numbers.stream().sorted(Comparator.reverseOrder()).limit(3).toList();
        print(19, "Top 3 largest numbers", result);
    }

    private static void problem20Skip3Take4(List<Integer> numbers) {
        List<Integer> result = numbers.stream().skip(3).limit(4).toList();
        print(20, "Skip 3 and take next 4", result);
    }

    private static void print(int number, String title, Object result) {
        System.out.println(number + ") " + title + " -> " + result);
    }
}
