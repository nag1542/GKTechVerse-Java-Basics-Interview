package com.gktechverse.corejava.modernjava.switchpatternmatching;

/**
 * Demonstrates switch pattern matching, record deconstruction, and guards.
 */
public class SwitchPatternMatchingDemo {

    public static void main(String[] args) {
        demoBeforeAndAfterAreaCalculation();
        demoRecordDestructuring();
        demoGuards();
        demoExhaustivenessBenefit();
    }

    private static void demoBeforeAndAfterAreaCalculation() {
        System.out.println("=== Switch Pattern Matching: before vs after ===");
        Shape circle = new Circle(3.0);
        Shape rectangle = new Rectangle(4.0, 5.0);
        Shape triangle = new Triangle(8.0, 6.0);

        System.out.println("before area(circle) = " + areaBefore(circle));
        System.out.println("before area(rectangle) = " + areaBefore(rectangle));
        System.out.println("before area(triangle) = " + areaBefore(triangle));

        System.out.println("switch area(circle) = " + areaWithSwitch(circle));
        System.out.println("switch area(rectangle) = " + areaWithSwitch(rectangle));
        System.out.println("switch area(triangle) = " + areaWithSwitch(triangle));
    }

    private static double areaBefore(Shape shape) {
        if (shape instanceof Circle) {
            Circle c = (Circle) shape;
            return Math.PI * c.radius() * c.radius();
        } else if (shape instanceof Rectangle) {
            Rectangle r = (Rectangle) shape;
            return r.width() * r.height();
        } else if (shape instanceof Triangle) {
            Triangle t = (Triangle) shape;
            return t.base() * t.height() / 2;
        }
        throw new IllegalStateException("Unknown shape");
    }

    private static double areaWithSwitch(Shape shape) {
        return switch (shape) {
            case Circle c -> Math.PI * c.radius() * c.radius();
            case Rectangle r -> r.width() * r.height();
            case Triangle t -> t.base() * t.height() / 2;
        };
    }

    private static void demoRecordDestructuring() {
        System.out.println("\n=== Record deconstruction in switch ===");
        System.out.println("deconstruct circle area = " + areaWithDeconstruction(new Circle(2.0)));
        System.out.println("deconstruct rectangle area = " + areaWithDeconstruction(new Rectangle(3.0, 7.0)));
        System.out.println("deconstruct triangle area = " + areaWithDeconstruction(new Triangle(10.0, 4.0)));
    }

    private static double areaWithDeconstruction(Shape shape) {
        return switch (shape) {
            case Circle(double radius) -> Math.PI * radius * radius;
            case Rectangle(double w, double h) -> w * h;
            case Triangle(double b, double h) -> b * h / 2;
        };
    }

    private static void demoGuards() {
        System.out.println("\n=== Guards in switch (real-world tagging) ===");
        System.out.println(describeShape(new Rectangle(120.0, 20.0)));
        System.out.println(describeShape(new Rectangle(80.0, 30.0)));
        System.out.println(describeShape(new Circle(5.0)));
    }

    private static String describeShape(Shape shape) {
        return switch (shape) {
            case Rectangle r when r.width() > 100 -> "Large Rectangle";
            case Rectangle r -> "Small Rectangle";
            default -> "Other";
        };
    }

    private static void demoExhaustivenessBenefit() {
        System.out.println("\n=== Exhaustiveness benefit ===");
        System.out.println("If you add a new permitted type (for example Square), the compiler forces switch updates.");
    }

    sealed interface Shape permits Circle, Rectangle, Triangle { }

    record Circle(double radius) implements Shape { }

    record Rectangle(double width, double height) implements Shape { }

    record Triangle(double base, double height) implements Shape { }
}
