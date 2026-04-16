package com.gktechverse.corejava.designpatterns.builder;

public class UserBuilderPatternDemo {

    public static void main(String[] args) {
        User user = User.builder("Alice", "alice@example.com")
                .age(30)
                .city("London")
                .emailVerified(true)
                .phoneVerified(false)
                .tier("FREE")
                .referralCode(null)
                .build();

        User premium = user.toBuilder()
                .tier("PREMIUM")
                .build();

        System.out.println("Original user: " + user);
        System.out.println("Upgraded user: " + premium);
    }
}
