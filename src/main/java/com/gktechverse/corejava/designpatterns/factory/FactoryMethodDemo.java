package com.gktechverse.corejava.designpatterns.factory;

public class FactoryMethodDemo {

    public static void main(String[] args) {
        Notification notification = NotificationFactory.create("EMAIL");
        notification.send("Order confirmed");
    }
}
