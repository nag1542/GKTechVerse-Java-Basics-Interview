package com.gktechverse.corejava.designpatterns.factory;

public class AbstractFactoryDemo {

    public static void main(String[] args) {
        renderLoginScreen(new WindowsUiFactory());
        renderLoginScreen(new MacUiFactory());
    }

    private static void renderLoginScreen(UiFactory factory) {
        Button button = factory.createButton();
        Checkbox checkbox = factory.createCheckbox();

        button.paint();
        checkbox.paint();
    }
}
