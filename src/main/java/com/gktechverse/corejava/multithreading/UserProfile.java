package com.gktechverse.corejava.multithreading;

/**
 * Simple immutable DTO used in fan-out examples.
 */
public record UserProfile(String userId, String tier, int engagementScore) {
}
