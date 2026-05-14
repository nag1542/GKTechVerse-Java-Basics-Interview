package com.gktechverse.corejava.modernjava.patternmatching;

/**
 * Demonstrates pattern matching for instanceof and switch with practical use-cases.
 */
public class PatternMatchingDemo {

    public static void main(String[] args) {
        demoInstanceOfBeforeAndAfter();
        demoPatternVariableScope();
        demoComplexCondition();
        demoRealWorldInputHandling();
        demoShapeAreaRealWorldUseCase();
    }

    private static void demoInstanceOfBeforeAndAfter() {
        System.out.println("=== Pattern Matching: instanceof (before vs after) ===");
        Object obj = "GKTechVerse";

        if (obj instanceof String) {
            String s = (String) obj;
            System.out.println("Before style length = " + s.length());
        }

        if (obj instanceof String s) {
            System.out.println("After style length = " + s.length());
        }
    }

    private static void demoPatternVariableScope() {
        System.out.println("\n=== Pattern variable scope rules ===");
        Object obj = "ScopeExample";

        if (obj instanceof String s) {
            System.out.println("Inside if scope: " + s.length()); // valid
        }
        System.out.println("Outside if, variable s is not accessible (compile-time error if used).");

        if (!(obj instanceof String s)) {
            return;
        }
        System.out.println("After guard-return, s is in scope: " + s.length());
    }

    private static void demoComplexCondition() {
        System.out.println("\n=== Complex condition with pattern variable ===");
        Object obj = "PatternMatch";

        if (obj instanceof String s && s.length() > 5) {
            System.out.println("Valid && usage: " + s);
        }

        System.out.println("Using '||' with pattern variable on RHS is invalid and does not compile.");
    }

    private static void demoRealWorldInputHandling() {
        System.out.println("\n=== Real-world usage: dynamic input processing ===");
        Object[] inputs = {"order-123", 42, 3.14};

        for (Object input : inputs) {
            if (input instanceof String s) {
                processString(s);
            } else if (input instanceof Integer i) {
                processNumber(i);
            } else {
                System.out.println("Unsupported input type: " + input.getClass().getSimpleName());
            }
        }
    }

    private static void processString(String value) {
        System.out.println("processString -> uppercase: " + value.toUpperCase());
    }

    private static void processNumber(Integer value) {
        System.out.println("processNumber -> squared: " + (value * value));
    }

    private static void demoShapeAreaRealWorldUseCase() {
        System.out.println("\n=== Real-world: calculate area for UI drawing shapes ===");
        Shape circle = new Circle(3.0);
        Shape rectangle = new Rectangle(4.0, 5.0);

        System.out.println("before area(circle) = " + areaBefore(circle));
        System.out.println("before area(rectangle) = " + areaBefore(rectangle));

        System.out.println("switch area(circle) = " + areaWithPatternSwitch(circle));
        System.out.println("switch area(rectangle) = " + areaWithPatternSwitch(rectangle));
    }

    private static double areaBefore(Shape shape) {
        if (shape instanceof Circle) {
            Circle c = (Circle) shape;
            return Math.PI * c.radius() * c.radius();
        } else if (shape instanceof Rectangle) {
            Rectangle r = (Rectangle) shape;
            return r.width() * r.height();
        }
        throw new IllegalStateException("Unknown shape: " + shape.getClass().getSimpleName());
    }

    private static double areaWithPatternSwitch(Shape shape) {
        return switch (shape) {
            case Circle c -> Math.PI * c.radius() * c.radius();
            case Rectangle r -> r.width() * r.height();
        };
    }

    sealed interface Shape permits Circle, Rectangle { }

    record Circle(double radius) implements Shape { }

    record Rectangle(double width, double height) implements Shape { }
}
