package com.gktechverse.corejava.streams;

import java.util.function.Function;

/**
 * Interview Q: Can a lambda access variables from outer scope?
 */
public class LambdaScopeAndEffectiveFinalDemo {

    private int counter = 0; // instance variable

    public static void main(String[] args) {
        LambdaScopeAndEffectiveFinalDemo demo = new LambdaScopeAndEffectiveFinalDemo();
        demo.showScopeRules();
    }

    private void showScopeRules() {
        System.out.println("\n--- Q1) Can a lambda access variables from the outer scope? ---");

        int multiplier = 3; // effectively final — value never changes

        Function<Integer, Integer> triple = n -> n * multiplier; // OK
        System.out.println("triple.apply(5): " + triple.apply(5)); // 15

        // multiplier = 4; // would break compilation
        // Error: Variable used in lambda expression should be final or effectively final.

        Runnable updateCounter = () -> {
            this.counter++; // instance variables are fine — no effectively-final restriction
            System.out.println("counter inside lambda: " + this.counter);
        };

        updateCounter.run();
        updateCounter.run();
        System.out.println("final counter value: " + this.counter);
    }
}
