package com.gktechverse.corejava.multithreading;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Pretend this service calls remote profile + score APIs.
 */
public class UserService {

    public UserProfile fetchProfile(String userId) throws IOException {
        simulateLatency(120, 260);

        if ("u3".equals(userId)) {
            throw new IOException("Profile API timeout for " + userId);
        }

        String tier = switch (userId) {
            case "u1", "u2" -> "GOLD";
            case "u4" -> "SILVER";
            default -> "BRONZE";
        };

        int score = ThreadLocalRandom.current().nextInt(55, 99);
        return new UserProfile(userId, tier, score);
    }

    public String fetchRecommendation(String userId) {
        simulateLatency(80, 200);
        return "Recommendation for " + userId;
    }

    private void simulateLatency(int min, int max) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(min, max));
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while calling remote service", exception);
        }
    }
}
