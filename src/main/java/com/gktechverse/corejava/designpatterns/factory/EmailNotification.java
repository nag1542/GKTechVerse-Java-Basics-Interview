package com.gktechverse.corejava.designpatterns.factory;

public class EmailNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("EMAIL sent: " + message);
    }
}
