package com.gktechverse.corejava.oops;

/**
 * Interview focus: class and object basics.
 * Creates an object and accesses its behavior.
 */
public class ClassObjectDemo {
    private String name;

    public ClassObjectDemo(String name) {
        this.name = name;
    }

    public void introduce() {
        System.out.println("Hello, I am object: " + name);
    }

    public static void main(String[] args) {
        ClassObjectDemo demoObject = new ClassObjectDemo("Student1");
        System.out.println("=== Class and Object Demo ===");
        demoObject.introduce();
    }
}
