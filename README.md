# Crack Core Java Interviews – Beginner

A beginner-friendly **Core Java 17 + Maven** project designed for interview preparation.
Each topic is organized in separate packages with small runnable demos and interview-oriented explanations.

## Project Purpose

This repository is structured as a practical guide for the playlist:

**"Crack Core Java Interviews – Beginner"**

It helps learners:
- revise Java fundamentals quickly,
- understand common interview concepts,
- run focused examples topic-by-topic.

## Tech Stack

- **Java Version:** 17
- **Build Tool:** Maven
- **Type:** Pure Core Java (no frameworks)

## Package Coverage

- `com.gktechverse.corejava.basics`
  - Variables, Data Types, Type Casting, Access Modifiers
- `com.gktechverse.corejava.jvm`
  - JVM Memory Areas, Class Loading, Boxing/Unboxing
- `com.gktechverse.corejava.oops`
  - Class/Object, Encapsulation, Abstraction, Inheritance, Polymorphism, Constructors,
    Interface vs Abstract Class, Final Keyword, Association/Aggregation/Composition,
    This Keyword, Super Keyword, Method Hiding
- `com.gktechverse.corejava.exceptions`
  - Checked vs Unchecked, Try-Catch-Finally, Custom Exception
- `com.gktechverse.corejava.strings`
  - String Pool, equals vs ==, StringBuilder vs StringBuffer, Immutability
- `com.gktechverse.corejava.collections`
  - Collections Framework interview basics with real enterprise scenarios
- `com.gktechverse.corejava.staticmemory`
  - Static keyword, static members, static blocks, Singleton vs static, Heap vs Stack, object creation flow
- `com.gktechverse.corejava.MainRunner`
  - Console menu to run demos by topic

## Playlist Link

> Add playlist URL here: `https://www.youtube.com/playlist?list=PLJIssYACbrL2UABIdAbvAZsfrEbFvHINI`

## How to Run

### 1) Compile project

```bash
mvn clean compile
```

### 2) Run full interactive menu

```bash
mvn exec:java
```

### 3) Run a specific class (example)

```bash
mvn -Dexec.mainClass="com.gktechverse.corejava.oops.PolymorphismDemo" exec:java
```

## Maven Commands (Quick Reference)

```bash
mvn clean
mvn compile
mvn test
mvn exec:java
```

## Static & Memory Concepts (with Code Examples)

Runnable demos are also available in package `com.gktechverse.corejava.staticmemory`:
- `StaticKeywordDemo`
- `StaticMembersAndBlockDemo`
- `StaticBlockVsVariableDemo`
- `SingletonVsStaticDemo`
- `HeapStackAndObjectCreationDemo`


### 1) What is the `static` keyword?
`static` means the member belongs to the **class itself**, not to individual objects.

```java
class Student {
    static String schoolName = "GKTechVerse"; // one shared value for all students
    String name; // separate value for each object
}
```

### 2) What are static variables and static methods?
- **Static variable (class variable):** shared by all objects.
- **Static method (class method):** can be called using class name; cannot directly access non-static fields.

```java
class Counter {
    static int count = 0; // shared

    Counter() {
        count++;
    }

    static void printCount() {
        System.out.println("Objects created = " + count);
    }
}

public class StaticVariableMethodDemo {
    public static void main(String[] args) {
        new Counter();
        new Counter();
        Counter.printCount(); // Objects created = 2
    }
}
```

### 3) When should we use static methods? What is a static block?
- Use static methods for **utility/common behavior** that does not depend on object state.
- A **static block** runs once when the class is loaded (before `main` or object creation).

```java
class MathUtil {
    static final double PI;

    static {
        PI = 3.14159;
        System.out.println("Static block executed: class loaded");
    }

    static int square(int n) {
        return n * n;
    }
}
```

### 4) Difference between static block and static variable
- **Static variable:** stores class-level data.
- **Static block:** initialization logic for static members.

```java
class Config {
    static String env; // data

    static {            // initialization logic
        env = "DEV";
    }
}
```

### 5) What is Singleton? How is it different from static?
- **Singleton:** design pattern that allows only one object instance.
- **Static:** class-level member access without creating objects.
- Singleton still creates an object; static members can exist without object creation.

```java
class AppConfig {
    private static final AppConfig INSTANCE = new AppConfig();

    private AppConfig() {}

    public static AppConfig getInstance() {
        return INSTANCE;
    }
}
```

### 6) Difference between Heap and Stack memory
- **Stack:** method calls, local variables, references (thread-specific, fast).
- **Heap:** objects and instance data (shared, managed by GC).

```java
public class MemoryDemo {
    public static void main(String[] args) {
        int x = 10;            // stack
        Person p = new Person(); // 'p' reference on stack, object on heap
        p.name = "Riya";
    }
}

class Person {
    String name;
}
```

### 7) What happens in memory when we create an object using `new`?
1. JVM checks class metadata (loads class if not loaded).
2. Memory is allocated in the heap.
3. Instance fields get default values.
4. Constructor runs and sets custom values.
5. Reference variable stores object address-like reference on stack.

```java
class Employee {
    int id;
    String name;

    Employee(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

public class ObjectCreationFlow {
    public static void main(String[] args) {
        Employee e = new Employee(101, "Aman");
        System.out.println(e.name);
    }
}
```

---

If this project helps you, star the repo and use it as your interview revision notebook.
