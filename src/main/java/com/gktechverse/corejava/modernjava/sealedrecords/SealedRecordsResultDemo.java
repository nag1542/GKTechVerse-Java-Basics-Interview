package com.gktechverse.corejava.modernjava.sealedrecords;

/**
 * Demonstrates sealed + record combination for explicit success/failure results.
 */
public class SealedRecordsResultDemo {

    public static void main(String[] args) {
        UserService userService = new UserService();

        System.out.println("=== Sealed + Records Result Demo ===");
        System.out.println(handleGetUser(userService.findById(1L)));
        System.out.println(handleGetUser(userService.findById(99L)));
    }

    private static ApiResponse<User> handleGetUser(Result<User> result) {
        return switch (result) {
            case Result.Success<User> success -> ApiResponse.ok(success.value());
            case Result.Failure<User> failure -> ApiResponse.badRequest(failure.message());
        };
    }

    sealed interface Result<T> permits Result.Success, Result.Failure {
        record Success<T>(T value) implements Result<T> { }

        record Failure<T>(String errorCode, String message) implements Result<T> { }
    }

    record User(long id, String email) { }

    static final class UserService {
        Result<User> findById(long id) {
            if (id == 1L) {
                return new Result.Success<>(new User(1L, "admin@gktechverse.com"));
            }
            return new Result.Failure<>("USER_NOT_FOUND", "User with id " + id + " not found");
        }
    }

    record ApiResponse<T>(int statusCode, String message, T body) {
        static <T> ApiResponse<T> ok(T body) {
            return new ApiResponse<>(200, "OK", body);
        }

        static <T> ApiResponse<T> badRequest(String message) {
            return new ApiResponse<>(400, message, null);
        }
    }

    // If you add this line, compiler will force updating permits/switch handling.
    // record Unauthorized<T>() implements Result<T> {}
}
