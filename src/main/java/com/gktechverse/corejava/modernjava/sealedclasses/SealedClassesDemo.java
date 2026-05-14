package com.gktechverse.corejava.modernjava.sealedclasses;

/**
 * Demonstrates why sealed classes/interfaces are useful.
 */
public class SealedClassesDemo {

    public static void main(String[] args) {
        demoProblemWithoutSealed();
        demoSolutionWithSealed();
    }

    private static void demoProblemWithoutSealed() {
        System.out.println("=== Problem: open interface allows unexpected implementations ===");

        ShapeLegacy circle = new CircleLegacy(3.0);
        ShapeLegacy rectangle = new RectangleLegacy(4.0, 5.0);
        ShapeLegacy triangle = new TriangleLegacy(8.0, 6.0);
        ShapeLegacy pentagon = new PentagonLegacy(10.0); // unexpected type

        System.out.println("area(circle) = " + areaLegacy(circle));
        System.out.println("area(rectangle) = " + areaLegacy(rectangle));
        System.out.println("area(triangle) = " + areaLegacy(triangle));

        try {
            System.out.println("area(pentagon) = " + areaLegacy(pentagon));
        } catch (IllegalStateException ex) {
            System.out.println("Bug surfaced: " + ex.getMessage());
        }
    }

    private static void demoSolutionWithSealed() {
        System.out.println("\n=== Solution: sealed Shape hierarchy ===");

        Shape circle = new Circle(3.0);
        Shape rectangle = new RectangleImpl(4.0, 5.0);
        Shape square = new Square(4.0);
        Shape triangle = new Triangle(8.0, 6.0);

        System.out.println("area(circle) = " + area(circle));
        System.out.println("area(rectangle) = " + area(rectangle));
        System.out.println("area(square) = " + area(square));
        System.out.println("area(triangle) = " + area(triangle));
        System.out.println("Only permitted types can implement Shape.");
    }

    private static double areaLegacy(ShapeLegacy shape) {
        if (shape instanceof CircleLegacy c) return Math.PI * c.radius() * c.radius();
        if (shape instanceof RectangleLegacy r) return r.width() * r.height();
        if (shape instanceof TriangleLegacy t) return 0.5 * t.base() * t.height();

        throw new IllegalStateException("Unknown shape: " + shape.getClass().getSimpleName());
    }

    private static double area(Shape shape) {
        if (shape instanceof Circle c) return Math.PI * c.radius() * c.radius();
        if (shape instanceof RectangleImpl r) return r.width() * r.height();
        if (shape instanceof Square s) return s.side() * s.side();
        if (shape instanceof Triangle t) return 0.5 * t.base() * t.height();

        throw new IllegalStateException("Unknown permitted shape: " + shape.getClass().getSimpleName());
    }

    interface ShapeLegacy { }

    record CircleLegacy(double radius) implements ShapeLegacy { }

    record RectangleLegacy(double width, double height) implements ShapeLegacy { }

    record TriangleLegacy(double base, double height) implements ShapeLegacy { }

    record PentagonLegacy(double side) implements ShapeLegacy { }

    sealed interface Shape permits Circle, Rectangle, Triangle { }

    static final class Circle implements Shape {
        private final double radius;

        Circle(double radius) {
            this.radius = radius;
        }

        double radius() {
            return radius;
        }
    }

    sealed static class Rectangle implements Shape permits Square, RectangleImpl { }

    static final class Square extends Rectangle {
        private final double side;

        Square(double side) {
            this.side = side;
        }

        double side() {
            return side;
        }
    }

    static final class RectangleImpl extends Rectangle {
        private final double width;
        private final double height;

        RectangleImpl(double width, double height) {
            this.width = width;
            this.height = height;
        }

        double width() {
            return width;
        }

        double height() {
            return height;
        }
    }

    non-sealed static class Triangle implements Shape {
        private final double base;
        private final double height;

        Triangle(double base, double height) {
            this.base = base;
            this.height = height;
        }

        double base() {
            return base;
        }

        double height() {
            return height;
        }
    }
}
