package com.gktechverse.corejava.oops;

/**
 * Interview focus: this keyword.
 * Uses this to refer current object's instance variables.
 */
public class ThisKeywordDemo {
    private String name;

    public ThisKeywordDemo(String name) {
        this.name = name;
    }

    public void printName() {
        System.out.println("this.name = " + this.name);
    }

    public static void main(String[] args) {
        ThisKeywordDemo demo = new ThisKeywordDemo("Core Java Student");
        System.out.println("=== This Keyword Demo ===");
        demo.printName();
    }
}
