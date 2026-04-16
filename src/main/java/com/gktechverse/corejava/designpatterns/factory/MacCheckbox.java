package com.gktechverse.corejava.designpatterns.factory;

public class MacCheckbox implements Checkbox {
    @Override
    public void paint() {
        System.out.println("Rendering Mac checkbox");
    }
}
