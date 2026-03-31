package com.gktechverse.corejava.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Same business task solved with loop and stream.
 */
public class LoopVsStreamDemo {

    public static void main(String[] args) {
        System.out.println("\n1) Loop vs Stream (same problem, same output)");

        List<Integer> amounts = Arrays.asList(1200, 900, 2500, 400, 1800, 3000);

        List<Integer> highValueLoop = new ArrayList<>();
        for (Integer amount : amounts) {
            if (amount >= 1500) {
                highValueLoop.add(amount);
            }
        }

        List<Integer> highValueStream = amounts.stream()
                .filter(amount -> amount >= 1500)
                .collect(Collectors.toList());

        System.out.println("Input amounts: " + amounts);
        System.out.println("Loop result (>= 1500): " + highValueLoop);
        System.out.println("Stream result (>= 1500): " + highValueStream);
    }
}
