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
- `com.gktechverse.corejava.MainRunner`
  - Console menu to run demos by topic

## Playlist Link

> Add playlist URL here: `https://youtube.com/your-playlist-link`

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

---

If this project helps you, star the repo and use it as your interview revision notebook.
