package com.gktechverse.corejava.oops;

/**
 * Interview focus: encapsulation.
 * Keeps fields private and controls access through getters/setters.
 */
public class EncapsulationDemo {
    private int marks;

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        if (marks >= 0 && marks <= 100) {
            this.marks = marks;
        }
    }

    public static void main(String[] args) {
        EncapsulationDemo student = new EncapsulationDemo();
        student.setMarks(85);

        System.out.println("=== Encapsulation Demo ===");
        System.out.println("Validated marks via setter: " + student.getMarks());
    }
}
