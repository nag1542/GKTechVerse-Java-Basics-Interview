package com.gktechverse.corejava.oops;

/**
 * Interview focus: association, aggregation, composition.
 * Demonstrates relationship strengths between classes.
 */
public class AssociationAggregationCompositionDemo {
    static class Teacher {
        String name;

        Teacher(String name) {
            this.name = name;
        }
    }

    static class Department {
        Teacher teacher; // aggregation: teacher can exist independently

        Department(Teacher teacher) {
            this.teacher = teacher;
        }
    }

    static class Engine {
        void start() {
            System.out.println("Engine starts.");
        }
    }

    static class Car {
        private final Engine engine = new Engine(); // composition: strong ownership

        void drive() {
            engine.start();
            System.out.println("Car drives.");
        }
    }

    public static void main(String[] args) {
        Teacher teacher = new Teacher("Ravi");
        Department dept = new Department(teacher);
        Car car = new Car();

        System.out.println("=== Association/Aggregation/Composition Demo ===");
        System.out.println("Association/Aggregation example: Department uses Teacher " + dept.teacher.name);
        car.drive();
    }
}
