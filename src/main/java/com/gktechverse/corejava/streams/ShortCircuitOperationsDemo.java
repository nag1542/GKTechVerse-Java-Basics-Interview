package com.gktechverse.corejava.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Pattern demo: short-circuit operations like findFirst and anyMatch.
 */
public class ShortCircuitOperationsDemo {

    public static void main(String[] args) {
        System.out.println("\n5) Short-circuit stream operations (findFirst, anyMatch)");

        List<Employee> employees = getEmployees();

        Optional<Employee> firstHighEarner = employees.stream()
                .filter(employee -> {
                    System.out.println("Checking salary for findFirst: " + employee.name());
                    return employee.salary() > 100000;
                })
                .findFirst();

        boolean hasHighEarner = employees.stream()
                .anyMatch(employee -> {
                    System.out.println("Checking salary for anyMatch: " + employee.name());
                    return employee.salary() > 100000;
                });

        System.out.println("firstHighEarner: " + firstHighEarner);
        System.out.println("hasHighEarner: " + hasHighEarner);
    }

    private static List<Employee> getEmployees() {
        return Arrays.asList(
                new Employee("E101", "Riya", "HR", 68000),
                new Employee("E102", "Karthik", "Engineering", 118000),
                new Employee("E103", "Aman", "Finance", 94000),
                new Employee("E104", "Sara", "Engineering", 132000)
        );
    }

    record Employee(String id, String name, String department, double salary) {
    }
}
