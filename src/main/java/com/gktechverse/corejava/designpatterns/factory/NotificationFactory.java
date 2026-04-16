package com.gktechverse.corejava.designpatterns.factory;

public class NotificationFactory {

    private NotificationFactory() {
    }

    public static Notification create(String type) {
        return switch (type) {
            case "EMAIL" -> new EmailNotification();
            case "SMS" -> new SmsNotification();
            case "PUSH" -> new PushNotification();
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };
    }
}
