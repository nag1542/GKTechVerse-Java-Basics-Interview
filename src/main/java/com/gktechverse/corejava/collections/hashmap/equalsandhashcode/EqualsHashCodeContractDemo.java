package com.gktechverse.corejava.collections.hashmap.equalsandhashcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Demonstrates why hashCode must be overridden whenever equals is overridden.
 *
 * Covers:
 * 1) Default Object.equals/hashCode behavior.
 * 2) Content-based equality using overridden equals.
 * 3) Problems in HashMap/HashSet when equals is overridden but hashCode is not.
 * 4) Correct behavior when both are overridden.
 * 5) Problems when equals/hashCode use different fields.
 * 6) Mutable key field pitfall in hash-based collections.
 */
public class EqualsHashCodeContractDemo {

    public static void main(String[] args) {
        System.out.println("\n5) equals() and hashCode() contract demo");
        defaultBehavior();
        equalsOverrideOnly();
        equalsWithoutHashCodeIssue();
        equalsAndHashCodeCorrect();
        differentFieldsIssue();
        mutableFieldIssue();
        missingInterviewUseCases();
    }

    private static void defaultBehavior() {
        System.out.println("\n1) Default behavior (Object class)");
        DefaultEmployee a = new DefaultEmployee("E101", "Asha");
        DefaultEmployee b = new DefaultEmployee("E101", "Asha");

        System.out.println("a == b : " + (a == b));
        System.out.println("a.equals(b): " + a.equals(b) + " (default Object.equals -> reference check)");
        System.out.println("a.hashCode: " + a.hashCode());
        System.out.println("b.hashCode: " + b.hashCode() + " (usually different for different objects)");
    }

    private static void equalsOverrideOnly() {
        System.out.println("\n2) Overriding equals() for content comparison");
        EqualsOnlyEmployee a = new EqualsOnlyEmployee("E101", "Asha");
        EqualsOnlyEmployee b = new EqualsOnlyEmployee("E101", "Asha");

        System.out.println("a.equals(b): " + a.equals(b) + " (same business content)");
        System.out.println("a.hashCode: " + a.hashCode());
        System.out.println("b.hashCode: " + b.hashCode() + " (still object-identity based)");
    }

    private static void equalsWithoutHashCodeIssue() {
        System.out.println("\n3) equals() overridden but hashCode() not overridden -> HashMap/HashSet issue");

        Set<EqualsOnlyEmployee> employees = new HashSet<>();
        employees.add(new EqualsOnlyEmployee("E101", "Asha"));
        employees.add(new EqualsOnlyEmployee("E101", "Asha"));
        System.out.println("HashSet size after adding logical duplicates: " + employees.size() + " (expected 1, got 2)");

        Map<EqualsOnlyEmployee, String> map = new HashMap<>();
        EqualsOnlyEmployee original = new EqualsOnlyEmployee("E102", "Ravi");
        map.put(original, "Engineering");

        EqualsOnlyEmployee lookupKey = new EqualsOnlyEmployee("E102", "Ravi");
        System.out.println("map.get(lookupKey): " + map.get(lookupKey) + " (expected Engineering, often null)");
    }

    private static void equalsAndHashCodeCorrect() {
        System.out.println("\n4) Overriding both equals() and hashCode() -> correct behavior");

        Set<ProperEmployee> employees = new HashSet<>();
        employees.add(new ProperEmployee("E201", "Meera"));
        employees.add(new ProperEmployee("E201", "Meera"));
        System.out.println("HashSet size after adding logical duplicates: " + employees.size() + " (correctly 1)");

        Map<ProperEmployee, String> map = new HashMap<>();
        map.put(new ProperEmployee("E202", "Kiran"), "Finance");
        System.out.println("map.get(new ProperEmployee(E202,Kiran)): "
                + map.get(new ProperEmployee("E202", "Kiran")) + " (correctly found)");
    }

    private static void differentFieldsIssue() {
        System.out.println("\n5) equals() and hashCode() using different fields -> contract broken");

        InconsistentEmployee a = new InconsistentEmployee("E301", "Nina", "HR");
        InconsistentEmployee b = new InconsistentEmployee("E301", "Nina", "IT");

        System.out.println("a.equals(b): " + a.equals(b) + " (dept ignored in equals)");
        System.out.println("a.hashCode == b.hashCode: " + (a.hashCode() == b.hashCode())
                + " (dept included in hashCode -> mismatch)");

        Set<InconsistentEmployee> set = new HashSet<>();
        set.add(a);
        set.add(b);
        System.out.println("HashSet size: " + set.size() + " (can become 2 even though equals says true)");
    }

    private static void mutableFieldIssue() {
        System.out.println("\n6) Mutable key field issue in hash-based collections");
        System.out.println("Important: immutable fields are safer; mutable fields in equals/hashCode are risky.");

        MutableEmployeeKey key = new MutableEmployeeKey("E401", "Pune");
        Map<MutableEmployeeKey, String> officeByEmployee = new HashMap<>();
        officeByEmployee.put(key, "India-West");

        System.out.println("Before mutation, map.get(key): " + officeByEmployee.get(key));
        key.setLocation("Bengaluru");
        System.out.println("After mutation, same object lookup map.get(key): " + officeByEmployee.get(key)
                + " (often null due to bucket change)");

        System.out.println("Teaching point: use immutable fields (prefer final) for keys used in equals/hashCode.");
    }

    private static void missingInterviewUseCases() {
        System.out.println("\nAdditional interview use-cases developers should know");
        System.out.println("- Contract rule: if a.equals(b) is true, a.hashCode() must equal b.hashCode().");
        System.out.println("- Reverse is not mandatory: same hashCode does not guarantee equals true.");
        System.out.println("- equals() should be null-safe, symmetric, transitive, and consistent.");
        System.out.println("- Use Objects.hash(...) and Objects.equals(...) for safer implementations.");
        System.out.println("- For inheritance, prefer composition or careful getClass()/instanceof strategy.");
    }

    private static final class DefaultEmployee {
        private final String id;
        private final String name;

        private DefaultEmployee(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    private static final class EqualsOnlyEmployee {
        private final String id;
        private final String name;

        private EqualsOnlyEmployee(String id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof EqualsOnlyEmployee that)) {
                return false;
            }
            return Objects.equals(id, that.id) && Objects.equals(name, that.name);
        }
    }

    private static final class ProperEmployee {
        private final String id;
        private final String name;

        private ProperEmployee(String id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof ProperEmployee that)) {
                return false;
            }
            return Objects.equals(id, that.id) && Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }
    }

    private static final class InconsistentEmployee {
        private final String id;
        private final String name;
        private final String department;

        private InconsistentEmployee(String id, String name, String department) {
            this.id = id;
            this.name = name;
            this.department = department;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof InconsistentEmployee that)) {
                return false;
            }
            return Objects.equals(id, that.id) && Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, department);
        }
    }

    private static final class MutableEmployeeKey {
        private final String employeeId;
        private String location;

        private MutableEmployeeKey(String employeeId, String location) {
            this.employeeId = employeeId;
            this.location = location;
        }

        private void setLocation(String location) {
            this.location = location;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof MutableEmployeeKey that)) {
                return false;
            }
            return Objects.equals(employeeId, that.employeeId)
                    && Objects.equals(location, that.location);
        }

        @Override
        public int hashCode() {
            return Objects.hash(employeeId, location);
        }
    }
}
