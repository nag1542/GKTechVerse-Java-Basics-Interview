package com.gktechverse.corejava.designpatterns.factory;

public class PushNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("PUSH sent: " + message);
    }
}
