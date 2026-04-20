package com.gktechverse.corejava.oops.interfacevsabstract;

/**
 * Interview focus: instance state difference.
 * Interface fields are constants; abstract classes can hold per-object state.
 */
public class InterfaceVsAbstractInstanceStateDemo {

    interface InterviewConstants {
        int X = 10; // public static final by default
    }

    abstract static class Person {
        protected String name;
        protected int age;

        protected Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        abstract String info();
    }

    static class Learner extends Person implements InterviewConstants {
        Learner(String name, int age) {
            super(name, age);
        }

        @Override
        String info() {
            return "Learner{name='" + name + "', age=" + age + "}";
        }
    }

    public static void main(String[] args) {
        Learner first = new Learner("Asha", 24);
        Learner second = new Learner("Rahul", 28);

        System.out.println("=== Interface vs Abstract: Instance State ===");
        System.out.println("Interface constant X = " + Learner.X);
        System.out.println("First object state : " + first.info());
        System.out.println("Second object state: " + second.info());
    }
}
