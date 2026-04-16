package com.gktechverse.corejava.designpatterns.factory;

public class WindowsButton implements Button {
    @Override
    public void paint() {
        System.out.println("Rendering Windows button");
    }
}
