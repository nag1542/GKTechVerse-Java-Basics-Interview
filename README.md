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
  - Checked vs Unchecked, Try-Catch-Finally, Custom Exception, Exception Handling Deep Dive, Checked Exception Handling Options, Try-with-Resources Internals, Multi-catch/Finally/Exception Chaining, 3 Exception Anti-Patterns
- `com.gktechverse.corejava.strings`
  - String Pool, equals vs ==, StringBuilder vs StringBuffer, Immutability
- `com.gktechverse.corejava.collections`
  - Collections Framework interview basics with real enterprise scenarios
- `com.gktechverse.corejava.collections.CollectionsEcommerceSelectionDemo`
  - Enterprise selection guide for List/Set/Map/Queue/PriorityQueue and fail-fast vs fail-safe iterators
- `com.gktechverse.corejava.collections.hashmap.*`
  - HashMap deep dive: null key/value behavior, duplicate keys, collision buckets, linked-list to tree conversion rules, and thread-safety limitations
- `com.gktechverse.corejava.collections.concurrenthashmap`
  - ConcurrentHashMap concurrency benefits and atomic update patterns
- `com.gktechverse.corejava.collections.ArrayListInternalWorkingDemo`
  - Next session: internal structure of ArrayList, add() flow, resize algorithm (1.5x growth), and operation complexity
- `com.gktechverse.corejava.collections.ArrayListMemoryOptimizationPatternsDemo`
  - Separate patterns session for pre-sizing, ensureCapacity(), and trimToSize() to avoid wasteful reallocations
- `com.gktechverse.corejava.collections.ArrayListVsLinkedListVsArrayDequeDemo`
  - Real-world comparison of ArrayList vs LinkedList vs ArrayDeque and when to use each
- `com.gktechverse.corejava.collections.ArrayListGotchasDemo`
  - 4 interview gotchas: Arrays.asList fixed-size list, autoboxing overhead, subList view behavior, and foreach fail-fast removal issue
- `com.gktechverse.corejava.staticmemory`
  - Static keyword, static members, static blocks, Singleton vs static, Heap vs Stack, object creation flow
- `com.gktechverse.corejava.multithreading`
  - Deadlock deep dive with enterprise real-time use cases, bad code vs good code solutions
- `com.gktechverse.corejava.streams`
  - Loop vs Stream, lazy evaluation behavior, lambda expressions & functional interfaces, and most useful stream patterns (grouping/counting/aggregation/flatMap/short-circuit/parallel-streams/top-20-practice)
- `com.gktechverse.corejava.solid`
  - SOLID design principles with real-world problem/solution demos (starting with pre-SOLID issue examples)
  - SRP demos: `solid.before.OrderServiceBeforeSolidDemo` and `solid.srp.SingleResponsibilityPrincipleDemo`
  - OCP demo: `solid.ocp.OpenClosedPrincipleDemo` (strategy-based discounts + StudentDiscount extension)
  - LSP demos: `solid.lsp.clear.LspBankAccountProblemDemo` and `solid.lsp.clear.LspBankAccountSolutionDemo`
  - ISP demos: `solid.isp.InterfaceSegregationProblemDemo` and `solid.isp.InterfaceSegregationSolutionDemo`
  - DIP demos: `solid.dip.DependencyInversionProblemDemo` and `solid.dip.DependencyInversionSolutionDemo`
- `com.gktechverse.corejava.MainRunner`
  - Console menu to run demos by topic


## Stream vs Loop (Detailed Interview Guide)

Both **loops** and **streams** solve collection-processing problems, but they shine in different scenarios.

### Quick definition
- **Loop (`for`, `while`)**: Imperative style. You control each step manually (index, condition, mutation, break/continue).
- **Stream (`stream()`)**: Declarative style. You describe operations as a pipeline (`filter`, `map`, `sorted`, `collect`).

### Same task with both approaches

```java
List<Integer> amounts = Arrays.asList(1200, 900, 2500, 400, 1800, 3000);

// Loop
List<Integer> highValueLoop = new ArrayList<>();
for (Integer amount : amounts) {
    if (amount >= 1500) {
        highValueLoop.add(amount);
    }
}

// Stream
List<Integer> highValueStream = amounts.stream()
        .filter(amount -> amount >= 1500)
        .toList();
```

### When Streams are better
1. **Transformation pipelines are clearer**
   - Example: Filter paid orders -> map to invoice DTO -> sort by amount -> collect list.
2. **Aggregate/reporting use cases**
   - `count`, `sum`, `max`, `groupingBy`, `partitioningBy` for dashboards and analytics.
3. **Readable business intent**
   - Code focuses on *what* is required, not iteration mechanics.
4. **Parallel processing (carefully)**
   - `parallelStream()` can help on CPU-heavy, independent operations over large data sets.

### When Loops are better
1. **Index-sensitive logic**
   - Example: comparing `arr[i]` with `arr[i-1]` (running differences, sliding windows).
2. **Early exit control flow**
   - `break` immediately on first fraud match / threshold breach.
3. **Complex mutation/state updates**
   - Multiple counters, flags, and branching conditions can be clearer in loops.
4. **Low-level performance tuning**
   - In very hot code paths, straightforward loops can reduce object/lambda overhead.

### Interview-ready comparison
- **Readability:** Streams often better for bulk operations; loops better for procedural logic.
- **Debugging:** Loops are easier for step-by-step debugging with mutable variables.
- **Performance:** Depends on workload, data size, JVM optimizations; benchmark before deciding.
- **Team convention:** Prefer the style your team can read and maintain quickly.

### Rule of thumb
- Use **Streams** for collection transformations and aggregations.
- Use **Loops** when you need explicit control, index access, or early termination.

> Runnable demo added in `CollectionsFrameworkInterviewDemo` under: **"Stream vs Loop: when should we use what?"**


## WHY LAMBDAS EXIST — THE REAL REASON

Lambdas were added to Java primarily to **pass behavior as data** with less boilerplate.
Before Java 8, this usually required anonymous classes that were verbose and harder to read.

```java
// Old style (Java 7): anonymous class
Collections.sort(names, new Comparator<String>() {
    @Override
    public int compare(String a, String b) {
        return a.compareTo(b);
    }
});

// Lambda style (Java 8): same behavior, less ceremony
names.sort((a, b) -> a.compareTo(b));

// Method reference style
names.sort(String::compareTo);
```

**Problem lambdas solve:** repetitive ceremony for small behavior blocks (sorting, filtering, callbacks, validation), which became even more important with Stream API pipelines.

**Yes — this definition is correct:** In Java you cannot pass a method as an argument directly. You pass an object. A lambda is a concise way to create an object of a functional interface that carries the behavior.



Runnable examples:
- `LambdaExpressionsAndFunctionalInterfacesDemo`
- `FunctionalInterfaceGreetingDemo`
- `LambdaFormsDemo`
- `Java8BuiltInFunctionalInterfacesDemo`
- `LambdaScopeAndEffectiveFinalDemo`
- `LambdaFunctionComposeVsAndThenDemo`
- `LambdaReusabilityVsStreamConsumptionDemo`
- `LambdaBiFunctionBiPredicateBiConsumerDemo`
- `LambdaProductionBugsMistakesDemo`


## Different Lambda Forms (Quick Guide)

```java
// Form 1 — No parameters
Runnable r = () -> System.out.println("running");

// Form 2 — One parameter — parentheses optional
Consumer<String> print = s -> System.out.println(s);

// Form 3 — Multiple parameters — parentheses required
Comparator<Integer> cmp = (a, b) -> a - b;

// Form 4 — Multi-line body — braces + explicit return
Function<String, Integer> parse = (s) -> {
    if (s == null) return 0;
    return Integer.parseInt(s);
};

// Form 5 — Method references
Consumer<String> print2 = System.out::println;
Function<String, Integer> len = String::length;
Supplier<List<String>> make = ArrayList::new;
```

Runnable demo class: `LambdaFormsDemo`.


## Java 8 Built-in Functional Interfaces (How to Use)

Key demos included in `Java8BuiltInFunctionalInterfacesDemo`:
- `Predicate<T>` composition using `.and()`, `.or()`, `.negate()`
- `Function<T,R>` composition using `.andThen()` and `.compose()`
- `Consumer<T>` chaining with `.andThen()` and usage in `stream().forEach()` / `Optional.ifPresent()`
- `Supplier<T>` for object/time creation and lazy fallback with `Optional.orElseGet(...)`
- `Collectors.toCollection(...)` with constructor reference supplier


## Interview Q1: Outer Scope Variables in Lambda

```java
int multiplier = 3; // effectively final
Function<Integer,Integer> triple = n -> n * multiplier;
System.out.println(triple.apply(5)); // 15

// multiplier = 4; // compile error: must be final or effectively final

this.counter++; // instance variable access is allowed inside lambda
```

Runnable demo class: `LambdaScopeAndEffectiveFinalDemo`.


## Interview Q2: Difference between `andThen` and `compose`

```java
Function<Integer,Integer> doubleIt = n -> n * 2;
Function<Integer,Integer> addTen   = n -> n + 10;

// andThen — left to right
doubleIt.andThen(addTen).apply(5);  // 20

// compose — right to left
doubleIt.compose(addTen).apply(5);  // 30
```

Runnable demo class: `LambdaFunctionComposeVsAndThenDemo`.


## Interview Q3: Are lambdas reusable?

```java
Predicate<String> isLong = s -> s.length() > 5;

names.stream().filter(isLong).forEach(System.out::println);
names.stream().filter(isLong).count();  // lambda reused safely

Stream<String> s = names.stream().filter(isLong);
s.count();    // OK
// s.findAny(); // IllegalStateException: stream already consumed
```

Key point: **lambda objects are reusable**, but a **Stream instance is single-use**.

Runnable demo class: `LambdaReusabilityVsStreamConsumptionDemo`.


## Interview Q5: What is a `BiFunction`?

```java
// BiFunction<T,U,R> — takes 2 inputs, returns 1 result
BiFunction<String, Integer, String> repeat = (s, n) -> s.repeat(n);
repeat.apply("ha", 3); // hahaha

// BiPredicate<T,U> — takes 2 inputs, returns boolean
BiPredicate<String,Integer> longerThan = (s, n) -> s.length() > n;

// BiConsumer<T,U> — takes 2 inputs, returns void
map.forEach((key, value) -> System.out.println(key + "=" + value));
```

Use these when your logic naturally needs **two inputs** (e.g., key+value, text+count, value+threshold).

Runnable demo class: `LambdaBiFunctionBiPredicateBiConsumerDemo`.


## 3 Lambda Mistakes That Cause Production Bugs

### Mistake 1 — Mutating state inside a lambda
```java
// int count = 0;
// names.forEach(n -> count++); // compile error (effectively-final rule)

int[] count = {0};
names.parallelStream().forEach(n -> count[0]++); // race condition

long safeCount = names.stream().filter(n -> n.startsWith("A")).count();
int total = numbers.stream().reduce(0, Integer::sum);
```

### Mistake 2 — `orElse` with expensive computation
```java
// orElse — argument evaluated ALWAYS
User user = findUser(id).orElse(createGuestUser());

// orElseGet — Supplier evaluated only when Optional is empty
User user = findUser(id).orElseGet(() -> createGuestUser());
```

Real impact: if `createGuestUser()` hits DB/service, `orElse` does extra work even when user exists.

### Mistake 3 — Complex logic buried inside a lambda
```java
users.stream()
    .filter(u -> u.getAge() > 18 && u.isActive() &&
            u.getSubscriptions().stream().anyMatch(s -> s.getType().equals("PREMIUM")))
    .toList();

// Better: extract to named method
users.stream().filter(this::isEligibleForPremium).toList();
```

Runnable demo class: `LambdaProductionBugsMistakesDemo`.

## Playlist Link

> Crack Core Java Interviews – Beginner: `https://www.youtube.com/playlist?list=PLJIssYACbrL2UABIdAbvAZsfrEbFvHINI`
> Java Interview Prep — Core Concepts & Real Production Explanations:`https://www.youtube.com/playlist?list=PLJIssYACbrL06EAvIexF4FiTWNHwbAhfd`

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

## Multithreading: Deadlock Detailed Explanation (Tech Video Notes)

Runnable demo classes:
- `com.gktechverse.corejava.multithreading.DeadlockEnterpriseUseCasesDemo`
- `com.gktechverse.corejava.multithreading.BankingTransferConsistencyAndDeadlockDemo`

Run directly:
```bash
mvn -Dexec.mainClass="com.gktechverse.corejava.multithreading.DeadlockEnterpriseUseCasesDemo" exec:java
mvn -Dexec.mainClass="com.gktechverse.corejava.multithreading.BankingTransferConsistencyAndDeadlockDemo" exec:java
```

### Why do threads need to "wait" in enterprise systems?
In real-time applications, multiple requests update the same shared data (ledger rows, stock tables, order records).
Without coordination, data becomes inconsistent (double debit, negative stock, duplicate order update).
So we use locks/synchronization to protect critical sections.

But poor locking strategy can cause **indefinite waiting**:
- Thread-1 holds Lock-A and waits for Lock-B.
- Thread-2 holds Lock-B and waits for Lock-A.
- Both are blocked forever -> this is deadlock.

### Deadlock Conditions (Coffman conditions simplified)
Deadlock usually appears when all conditions are true:
1. **Mutual exclusion** – resource can be used by one thread at a time.
2. **Hold and wait** – thread holds one lock and waits for another.
3. **No preemption** – lock cannot be forcefully taken away.
4. **Circular wait** – circular chain of threads waiting on each other.

Break even one condition to avoid deadlock.

---


### Demo-2: Banking System (A and B send money at exact same millisecond)

#### Case A: Without lock (no synchronization)
If transfer logic updates balances without a lock, debit/credit is not atomic:
- Thread-1 may read old balance of A and write new value.
- At the same time, Thread-2 may also read/write stale values.
- One update can overwrite another update (lost update).

Impact:
- Total money may become inconsistent in memory.
- Customer balances can appear wrong temporarily or permanently.
- Audit/reconciliation effort increases.

#### Case B: Bad locking code
If we add nested locks in different order:
- A->B flow: lock A then lock B.
- B->A flow: lock B then lock A.

Issue:
- Both threads can block forever (deadlock).
- Transactions timeout and payment throughput drops.

#### Case C: Good locking code (ordered lock)
Use one global order for lock acquisition:
- Always lock lower account-id first, then higher account-id.

Why this solves:
- Circular wait is removed.
- Threads may wait briefly, but they do not wait forever.
- Balances stay consistent and operations complete reliably.

### Use Case 1: Banking Settlement Service

#### BAD CODE pattern (problem)
Two parallel transfers lock ledgers in opposite order:
- A->B flow locks `BANK-A`, then tries `BANK-B`.
- B->A flow locks `BANK-B`, then tries `BANK-A`.

Result:
- Both threads keep waiting.
- Settlement queue blocks.
- SLA breaches and reconciliation delays happen.

#### GOOD CODE pattern (solution)
Use **global lock ordering**:
- Always acquire ledger locks in deterministic order (e.g., lexical order or by account id).
- Even if request direction is opposite, lock order stays same.

Why it works:
- Circular wait is removed.
- Threads may still wait briefly, but they eventually proceed.

---

### Use Case 2: E-commerce Order + Inventory Services

#### BAD CODE pattern (problem)
- Checkout flow locks `ORDER` then `INVENTORY`.
- Reconciliation flow locks `INVENTORY` then `ORDER`.

Result:
- Checkout freezes intermittently during traffic spikes.
- Inventory adjustments stop.
- Users see failures/timeouts.

#### GOOD CODE pattern (solution)
Use **`ReentrantLock#tryLock(timeout)` + retry/backoff**:
- Try acquiring first lock with timeout.
- Try second lock with timeout.
- If second lock fails: release first lock immediately, wait briefly (backoff), retry.

Why it works:
- No infinite waiting.
- System degrades gracefully under contention.
- Helps preserve availability in high traffic windows.

---

### Bad Coding vs Good Coding Checklist

#### Bad coding (risk of deadlock)
- Lock order depends on request path.
- Nested synchronized blocks across multiple resources without standard order.
- No timeout/no rollback strategy.
- Large critical sections with DB/API calls inside lock.

#### Good coding (safe & scalable)
- Define and enforce one lock acquisition order across services.
- Prefer timed lock attempts (`tryLock`) for cross-resource operations.
- Keep lock scope very small (only critical mutation logic).
- Release lock quickly; never do long I/O under lock.
- Add retries, backoff, and observability (thread dumps, metrics, lock wait logs).

---

### Interview-friendly one-liner
**Deadlock is not just a Java syntax issue; it is a production reliability issue caused by inconsistent resource locking across concurrent business flows.**

---

If this project helps you, star the repo and use it as your interview revision notebook.

## Java Collections Enterprise Notes

A dedicated guide is available at `JAVA_COLLECTIONS_README.md` covering when to use `ArrayList`, `LinkedList`, `HashSet`, `LinkedHashSet`, `TreeSet`, `HashMap`, `LinkedHashMap`, `TreeMap`, `Queue`, `PriorityQueue`, and iterator behavior (fail-fast vs fail-safe).
