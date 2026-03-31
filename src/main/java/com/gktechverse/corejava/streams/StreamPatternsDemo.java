package com.gktechverse.corejava.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Useful stream patterns asked in interviews.
 */
public class StreamPatternsDemo {

    public static void main(String[] args) {
        System.out.println("\n3) Most useful stream patterns");

        List<Employee> employees = getEmployees();

        // Pattern 1: Group employees by department
        Map<String, List<Employee>> byDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));

        // Pattern 1 (part 2): Count employees per department
        Map<String, Long> countByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.counting()
                ));

        // Pattern 2: Find top paid employee
        Employee topPaid = employees.stream()
                .max((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()))
                .orElseThrow();

        // Pattern 3: Department wise average salary
        Map<String, Double> averageSalaryByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.averagingDouble(Employee::getSalary)
                ));

        System.out.println("Employees: " + employees);
        System.out.println("Grouped by department: " + byDept);
        System.out.println("Count by department: " + countByDept);
        System.out.println("Top paid employee: " + topPaid);
        System.out.println("Average salary by department: " + averageSalaryByDept);
    }

    private static List<Employee> getEmployees() {
        return Arrays.asList(
                new Employee("E101", "Anita", "Engineering", 120000),
                new Employee("E102", "Rahul", "Engineering", 138000),
                new Employee("E103", "Kiran", "Finance", 98000),
                new Employee("E104", "Divya", "HR", 90000),
                new Employee("E105", "Sanjay", "Finance", 112000),
                new Employee("E106", "Neha", "HR", 95000)
        );
    }

    static class Employee {
        private final String id;
        private final String name;
        private final String department;
        private final double salary;

        Employee(String id, String name, String department, double salary) {
            this.id = id;
            this.name = name;
            this.department = department;
            this.salary = salary;
        }

        String getDepartment() {
            return department;
        }

        double getSalary() {
            return salary;
        }

        @Override
        public String toString() {
            return "Employee{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", department='" + department + '\'' +
                    ", salary=" + salary +
                    '}';
        }
    }
}
