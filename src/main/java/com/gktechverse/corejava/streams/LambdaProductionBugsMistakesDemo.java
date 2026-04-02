package com.gktechverse.corejava.streams;

import java.util.List;
import java.util.Optional;

/**
 * Interview topic: 3 Lambda mistakes that cause production bugs.
 */
public class LambdaProductionBugsMistakesDemo {

    private static int guestUserDbHits = 0;

    public static void main(String[] args) {
        System.out.println("\n--- 3 Lambda Mistakes That Cause Production Bugs ---");

        mistake1MutatingStateInsideLambda();
        mistake2OrElseWithExpensiveComputation();
        mistake3ComplexLogicBuriedInsideLambda();
    }

    private static void mistake1MutatingStateInsideLambda() {
        System.out.println("\nMistake 1: Mutating state inside a lambda");

        List<String> names = List.of("Alice", "Bob", "Anna", "Alex", "Mira");
        List<Integer> numbers = List.of(10, 20, 30, 40);

        // Does not compile — Java protects you here:
        // int count = 0;
        // names.forEach(n -> count++); // Error: must be final or effectively final

        // Compiles but WRONG in parallel streams — race condition
        int[] unsafeCount = {0};
        names.parallelStream().forEach(n -> unsafeCount[0]++);
        System.out.println("Unsafe parallel count (race-prone): " + unsafeCount[0]);

        // Correct approaches: use stream terminal ops for accumulation
        long safeCount = names.stream().filter(n -> n.startsWith("A")).count();
        int total = numbers.stream().reduce(0, Integer::sum);
        System.out.println("Safe count using count(): " + safeCount);
        System.out.println("Safe sum using reduce(): " + total);
    }

    private static void mistake2OrElseWithExpensiveComputation() {
        System.out.println("\nMistake 2: orElse with expensive computation");

        guestUserDbHits = 0;
        User user1 = findUser("u100").orElse(createGuestUser()); // always evaluates argument
        System.out.println("orElse resolved user: " + user1.name());
        System.out.println("DB hits after orElse with existing user: " + guestUserDbHits);

        guestUserDbHits = 0;
        User user2 = findUser("u100").orElseGet(LambdaProductionBugsMistakesDemo::createGuestUser); // lazy
        System.out.println("orElseGet resolved user: " + user2.name());
        System.out.println("DB hits after orElseGet with existing user: " + guestUserDbHits);

        // Real impact: createGuestUser() could hit DB/service/cache every time with orElse
        // With orElseGet, expensive fallback runs only when Optional is empty.
    }

    private static void mistake3ComplexLogicBuriedInsideLambda() {
        System.out.println("\nMistake 3: Complex logic buried inside a lambda");

        List<AppUser> users = List.of(
                new AppUser("Asha", 28, true, List.of(new Subscription("PREMIUM"), new Subscription("NEWS"))),
                new AppUser("Ravi", 17, true, List.of(new Subscription("PREMIUM"))),
                new AppUser("Kiran", 31, false, List.of(new Subscription("PREMIUM"))),
                new AppUser("Mina", 25, true, List.of(new Subscription("BASIC")))
        );

        // Hard to read and hard to unit test when everything is embedded inline
        List<AppUser> inlineResult = users.stream()
                .filter(u -> u.age() > 18
                        && u.active()
                        && u.subscriptions().stream().anyMatch(s -> "PREMIUM".equals(s.type())))
                .toList();

        // Better: extract to named method, easier to read and test
        List<AppUser> extractedResult = users.stream()
                .filter(LambdaProductionBugsMistakesDemo::isEligibleForPremium)
                .toList();

        System.out.println("Inline result size: " + inlineResult.size());
        System.out.println("Extracted-method result size: " + extractedResult.size());
    }

    private static boolean isEligibleForPremium(AppUser user) {
        return user.age() > 18
                && user.active()
                && user.subscriptions().stream().anyMatch(s -> "PREMIUM".equals(s.type()));
    }

    private static Optional<User> findUser(String id) {
        if ("u100".equals(id)) {
            return Optional.of(new User(id, "Alice"));
        }
        return Optional.empty();
    }

    private static User createGuestUser() {
        guestUserDbHits++;
        System.out.println("createGuestUser() invoked (simulated DB hit)");
        return new User("guest", "Guest User");
    }

    record User(String id, String name) {
    }

    record AppUser(String name, int age, boolean active, List<Subscription> subscriptions) {
    }

    record Subscription(String type) {
    }
}
