package com.gktechverse.corejava.designpatterns.builder;

public class User {
    private final String name;
    private final String email;
    private final int age;
    private final String city;
    private final boolean emailVerified;
    private final boolean phoneVerified;
    private final String tier;
    private final String referralCode;

    private User(Builder builder) {
        this.name = builder.name;
        this.email = builder.email;
        this.age = builder.age;
        this.city = builder.city;
        this.emailVerified = builder.emailVerified;
        this.phoneVerified = builder.phoneVerified;
        this.tier = builder.tier;
        this.referralCode = builder.referralCode;
    }

    public static Builder builder(String name, String email) {
        return new Builder(name, email);
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static class Builder {
        private final String name;
        private final String email;

        private int age = 0;
        private String city = "N/A";
        private boolean emailVerified = false;
        private boolean phoneVerified = false;
        private String tier = "FREE";
        private String referralCode;

        public Builder(String name, String email) {
            this.name = name;
            this.email = email;
        }

        private Builder(User user) {
            this.name = user.name;
            this.email = user.email;
            this.age = user.age;
            this.city = user.city;
            this.emailVerified = user.emailVerified;
            this.phoneVerified = user.phoneVerified;
            this.tier = user.tier;
            this.referralCode = user.referralCode;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder city(String city) {
            this.city = city;
            return this;
        }

        public Builder emailVerified(boolean emailVerified) {
            this.emailVerified = emailVerified;
            return this;
        }

        public Builder phoneVerified(boolean phoneVerified) {
            this.phoneVerified = phoneVerified;
            return this;
        }

        public Builder tier(String tier) {
            this.tier = tier;
            return this;
        }

        // Backward-friendly alias if someone still calls plan(...)
        public Builder plan(String plan) {
            return tier(plan);
        }

        public Builder referralCode(String referralCode) {
            this.referralCode = referralCode;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", city='" + city + '\'' +
                ", emailVerified=" + emailVerified +
                ", phoneVerified=" + phoneVerified +
                ", tier='" + tier + '\'' +
                ", referralCode='" + referralCode + '\'' +
                '}';
    }
}
