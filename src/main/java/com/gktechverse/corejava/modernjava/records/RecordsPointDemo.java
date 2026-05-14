package com.gktechverse.corejava.modernjava.records;

import java.util.List;
import java.util.Objects;

/**
 * Demonstrates practical Java record use-cases.
 */
public class RecordsPointDemo {

    public static void main(String[] args) {
        demoBeforeAndAfterPoint();
        demoRangeValidation();
        demoCreateUserRequestValidation();
        demoEmailTransformation();
        demoDtoUsage();
        demoApiRequestResponse();
        demoMultipleReturnValues();
        demoConfigurationObject();
        demoWrongUsage();
    }

    private static void demoBeforeAndAfterPoint() {
        System.out.println("=== Before vs After: Point ===");
        PointBefore beforeP1 = new PointBefore(1, 2);
        PointBefore beforeP2 = new PointBefore(1, 2);
        System.out.println("Before equals = " + beforeP1.equals(beforeP2));

        Point p1 = new Point(1, 2);
        Point p2 = new Point(1, 2);
        System.out.println("Record equals = " + p1.equals(p2));
    }

    private static void demoRangeValidation() {
        System.out.println("\n=== Compact Constructor: Range Validation ===");
        Range validRange = new Range(5, 10);
        System.out.println("validRange = " + validRange);

        try {
            new Range(10, 5);
        } catch (IllegalArgumentException ex) {
            System.out.println("new Range(10, 5) failed: " + ex.getMessage());
        }
    }

    private static void demoCreateUserRequestValidation() {
        System.out.println("\n=== Compact Constructor: CreateUserRequest Validation ===");
        CreateUserRequest validRequest = new CreateUserRequest("dev@gktechverse.com", "StrongPwd123", "USER");
        System.out.println("validRequest = " + validRequest);
    }

    private static void demoEmailTransformation() {
        System.out.println("\n=== Compact Constructor: Data Transformation ===");
        Email email = new Email("  GKTechVerse@Example.COM  ");
        System.out.println("Normalized email = " + email.value());
    }

    private static void demoDtoUsage() {
        System.out.println("\n=== Real Usage 1: DTO Before vs After ===");
        UserDtoBefore beforeDto = new UserDtoBefore(101L, "user@gktechverse.com", "ADMIN");
        UserDto userDto = new UserDto(101L, "user@gktechverse.com", "ADMIN");
        System.out.println("Before DTO email = " + beforeDto.getEmail());
        System.out.println("Record DTO email = " + userDto.email());
    }

    private static void demoApiRequestResponse() {
        System.out.println("\n=== Real Usage 2: API Request/Response ===");
        CreateUserRequest request = new CreateUserRequest("api@gktechverse.com", "StrongPwd123", "ADMIN");
        ApiResponse response = new ApiResponse("SUCCESS", "User created: " + request.email());
        System.out.println("request = " + request);
        System.out.println("response = " + response);
    }

    private static void demoMultipleReturnValues() {
        System.out.println("\n=== Real Usage 3: Return Multiple Values ===");
        PageResult<String> result = new PageResult<>(List.of("Alice", "Bob", "Charlie"), 3L, 1);
        System.out.println("items = " + result.items());
        System.out.println("total = " + result.total() + ", page = " + result.page());
    }

    private static void demoConfigurationObject() {
        System.out.println("\n=== Real Usage 4: Configuration Object ===");
        DatabaseConfig config = new DatabaseConfig(
                "jdbc:postgresql://localhost:5432/app_db",
                "app_user",
                20
        );
        System.out.println("db config = " + config);
    }

    private static void demoWrongUsage() {
        System.out.println("\n=== Wrong Usage Example ===");
        System.out.println("Records are immutable. This is illegal and will not compile:");
        System.out.println("record BankAccount(double balance) {");
        System.out.println("    void deposit(double amount) {");
        System.out.println("        balance += amount; // illegal");
        System.out.println("    }");
        System.out.println("}");
    }

    public static final class PointBefore {
        private final int x;
        private final int y;

        public PointBefore(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int x() { return x; }

        public int y() { return y; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof PointBefore other)) return false;
            return x == other.x && y == other.y;
        }

        @Override
        public int hashCode() {
            int result = Integer.hashCode(x);
            result = 31 * result + Integer.hashCode(y);
            return result;
        }

        @Override
        public String toString() {
            return "PointBefore[x=" + x + ", y=" + y + "]";
        }
    }

    public static final class UserDtoBefore {
        private final long id;
        private final String email;
        private final String role;

        public UserDtoBefore(long id, String email, String role) {
            this.id = id;
            this.email = email;
            this.role = role;
        }

        public long getId() { return id; }

        public String getEmail() { return email; }

        public String getRole() { return role; }
    }

    public record Point(int x, int y) { }

    public record Range(int min, int max) {
        public Range {
            if (min > max) {
                throw new IllegalArgumentException("min must be <= max");
            }
        }
    }

    public record UserDto(long id, String email, String role) { }

    public record CreateUserRequest(String email, String password, String role) {
        public CreateUserRequest {
            Objects.requireNonNull(email, "email required");
            Objects.requireNonNull(password, "password required");
            Objects.requireNonNull(role, "role required");

            if (!email.contains("@")) {
                throw new IllegalArgumentException("Invalid email");
            }
            if (password.length() < 8) {
                throw new IllegalArgumentException("Weak password");
            }
        }
    }

    public record ApiResponse(String status, String message) { }

    public record PageResult<T>(List<T> items, long total, int page) { }

    public record DatabaseConfig(String url, String username, int maxPoolSize) { }

    public record Email(String value) {
        public Email {
            Objects.requireNonNull(value, "email value required");
            value = value.trim().toLowerCase();
        }
    }
}
