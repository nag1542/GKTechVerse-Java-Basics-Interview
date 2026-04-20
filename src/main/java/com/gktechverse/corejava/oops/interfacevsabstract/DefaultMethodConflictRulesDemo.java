package com.gktechverse.corejava.oops.interfacevsabstract;

/**
 * Interview focus: default method conflict resolution rules.
 * Rule 1: class method wins.
 * Rule 2: more specific interface wins.
 * Rule 3: ambiguous defaults force explicit override.
 */
public class DefaultMethodConflictRulesDemo {

    interface Logger {
        default String format(String msg) {
            return "[LOG] " + msg;
        }
    }

    interface Auditor {
        default String format(String msg) {
            return "[AUDIT] " + msg;
        }
    }

    // Rule 1: class method wins over interface default method.
    static class ClassWinsService implements Logger {
        @Override
        public String format(String msg) {
            return "Custom: " + msg;
        }
    }

    interface BaseLogger {
        default String format(String msg) {
            return "[BASE] " + msg;
        }
    }

    interface DetailedLogger extends BaseLogger {
        @Override
        default String format(String msg) {
            return "[DETAILED] " + msg;
        }
    }

    // Rule 2: more specific interface (child) default method wins.
    static class MoreSpecificInterfaceWinsService implements DetailedLogger {
    }

    // Rule 3: ambiguous defaults from unrelated interfaces require explicit override.
    static class AmbiguousServiceResolvesConflict implements Logger, Auditor {
        @Override
        public String format(String msg) {
            return Logger.super.format(msg); // or Auditor.super.format(msg)
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Default Method Conflict Rules Demo ===");

        ClassWinsService classWins = new ClassWinsService();
        System.out.println("Rule 1 - Class wins: " + classWins.format("Saved successfully"));

        MoreSpecificInterfaceWinsService specificWins = new MoreSpecificInterfaceWinsService();
        System.out.println("Rule 2 - More specific interface wins: " + specificWins.format("Cache refreshed"));

        AmbiguousServiceResolvesConflict resolved = new AmbiguousServiceResolvesConflict();
        System.out.println("Rule 3 - Explicit override required: " + resolved.format("Payment reviewed"));

        System.out.println("Note: Without override in AmbiguousServiceResolvesConflict, code will not compile.");
    }
}
