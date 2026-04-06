# Java Collections Framework (E-commerce Enterprise Guide)

This guide explains **when to use which collection** with real e-commerce examples using `Order` and `Product` context.

## Quick Decision Matrix

- Need ordered list with duplicates -> `List`
- Need uniqueness -> `Set`
- Need key-value lookup -> `Map`
- Need processing queue -> `Queue`
- Need priority processing -> `PriorityQueue`

---

## 1) List

### `ArrayList`
**Use when:**
- Most operations are reads and indexed access.
- You append frequently.

**Why:** dynamic array, fast `get(index)`.

**E-commerce example:** cart items, paginated product results.

### `LinkedList`
**Use when:**
- Frequent add/remove at head/tail.
- Queue/deque style workflow.

**Why:** doubly linked nodes; indexed access slower than `ArrayList`.

**E-commerce example:** packing/dispatch pipeline queue.

**Choose:**
- Default -> `ArrayList`
- Frequent end insert/remove + queue behavior -> `LinkedList`

---

## 2) Set

### `HashSet`
**Use when:** uniqueness and fastest membership checks.

**E-commerce example:** unique purchased SKU ids.

### `LinkedHashSet`
**Use when:** uniqueness + insertion order preserved.

**E-commerce example:** recently viewed products in view order.

### `TreeSet`
**Use when:** uniqueness + sorted order needed.

**E-commerce example:** sorted discount rates, sorted price points.

**Choose:**
- Fast unordered uniqueness -> `HashSet`
- Unique + stable insertion order -> `LinkedHashSet`
- Unique + sorted -> `TreeSet`

---

## 3) Map

### `HashMap`
**Use when:** default key-value lookup.

**E-commerce example:** `productId -> Product`, `orderId -> Order`.

### `LinkedHashMap`
**Use when:** key-value + insertion order (or access order for cache-like behavior).

**E-commerce example:** recently accessed orders list.

### `TreeMap`
**Use when:** sorted keys and range lookups.

**E-commerce example:** price band mapping and ceiling/floor queries.

**Choose:**
- Default map -> `HashMap`
- Predictable iteration order -> `LinkedHashMap`
- Sorted/range operations -> `TreeMap`

---

## 4) Queue and PriorityQueue

### `Queue` (FIFO)
**Use when:** first-in-first-out processing.

**E-commerce example:** order lifecycle events queue.

### `PriorityQueue`
**Use when:** priority-based processing.

**E-commerce example:** express/high-risk/high-SLA orders processed first.

> Note: `PriorityQueue` guarantees priority order on `poll()`, not full sorted iteration.

---

## 5) Iterators: Fail-Fast vs Fail-Safe

### Fail-Fast (e.g., `ArrayList`, `HashMap` iterators)
- Throws `ConcurrentModificationException` when collection is structurally modified during iteration.
- Use iterator's own `remove()` for safe deletion while iterating.

### Fail-Safe style (snapshot/weakly-consistent)
- Example: `CopyOnWriteArrayList` iterator.
- Iteration proceeds safely even if collection is modified; may observe snapshot behavior.

---

## Runnable Demo in this Repository

Use:

```bash
mvn -Dexec.mainClass="com.gktechverse.corejava.collections.CollectionsEcommerceSelectionDemo" exec:java
```

This class demonstrates all requested collection types with practical order/product scenarios.


## Next Session: How ArrayList Works Internally in Java

```java
List<String> names = new ArrayList<>();
```

### ArrayList Internal Structure
- Backed by a resizable `Object[]` array.
- `size` = current elements count, `capacity` = internal array length.
- Capacity grows automatically when full.

### How `add()` Works and the Resize Algorithm
- Append inserts at `elementData[size]`, then increments `size`.
- If full, it creates a larger array and copies elements.
- Growth is typically ~1.5x: `newCapacity = oldCapacity + (oldCapacity >> 1)`.
- Result: occasional costly resize, but efficient amortized appends.

```java
// Simplified from JDK source
public boolean add(E e) {
    ensureCapacity(size + 1);      // grow if needed
    elementData[size] = e;         // write at next slot
    size++;
    return true;
}

private void ensureCapacity(int minCapacity) {
    if (minCapacity > elementData.length) {
        grow(minCapacity);
    }
}

private void grow(int minCapacity) {
    int oldCapacity = elementData.length;
    int newCapacity = oldCapacity + (oldCapacity >> 1); // × 1.5
    // oldCapacity >> 1  =  oldCapacity / 2
    // So: 10 → 15 → 22 → 33 → 49 → 73...
    elementData = Arrays.copyOf(elementData, newCapacity);
}
```

### Operation Complexity
- `get(index)` -> **O(1)**
- `set(index, value)` -> **O(1)**
- `add(e)` at end -> **O(1)** amortized (worst **O(n)** during resize)
- `add(index, e)` -> **O(n)**
- `remove(index)` -> **O(n)**
- `contains(e)` -> **O(n)**
- Iteration -> **O(n)**

Runnable demo classes: `com.gktechverse.corejava.collections.ArrayListInternalWorkingDemo` and `com.gktechverse.corejava.collections.ArrayListMemoryOptimizationPatternsDemo`.


### How to Use ArrayList Without Wasting Memory

#### Pattern 1 — Pre-size when you know the count
```java
// Reading 10,000 rows from a database

// ❌ Default capacity 10 — triggers many resizes
List<User> users = new ArrayList<>();
while (rs.next()) { users.add(mapRow(rs)); }
// Resizes at: 10,15,22,33,49,73,109... — multiple Array.copyOf calls

// ✅ Pre-size — zero resizes
int rowCount = getCount(query);  // SELECT COUNT(*) first
List<User> users = new ArrayList<>(rowCount);
while (rs.next()) { users.add(mapRow(rs)); }

// Rule: if you know the size upfront, always pass it to the constructor
```

#### Pattern 2 — `ensureCapacity()` for batch adds
```java
// Adding elements in a loop where you know the total count
List<String> results = new ArrayList<>();
results.ensureCapacity(batchSize);  // pre-allocate before loop

for (Item item : batch) {
    results.add(transform(item));
}
// No resizes happen during the loop
```

#### Pattern 3 — `trimToSize()` after bulk loading
```java
// You loaded 847 items into an ArrayList
// Capacity is now 849 (next step after 849 * 1.5...)
// Those extra slots waste memory — especially if this list lives long

List<Product> catalogue = new ArrayList<>();
catalogue.addAll(loadFromDatabase());
catalogue.trimToSize();  // shrinks backing array to exactly size

// Use when: list is built once, never grows again
// Do not use when: list is frequently modified after loading
```


## ArrayList vs LinkedList vs ArrayDeque (Real-World Guide)

### Quick comparison
- **ArrayList**: dynamic array, fast indexed reads, slower middle insert/remove due to shifts.
- **LinkedList**: doubly-linked list, efficient add/remove at ends, slower random access.
- **ArrayDeque**: circular dynamic array, very fast queue/stack operations at both ends.

### Real-world examples
- **ArrayList**: Product listing/search results page where users scroll and app frequently reads by index.
- **LinkedList**: Multi-step workflow where states are frequently added/removed from front/back.
- **ArrayDeque**: High-throughput order/event queue for FIFO processing.

### When to use what
- Use **ArrayList** for read-heavy data with frequent appends.
- Use **LinkedList** only when end insert/remove patterns dominate and random access is rare.
- Use **ArrayDeque** for queue/stack/deque workloads; usually preferred over `LinkedList` for queue usage.

Runnable demo class: `com.gktechverse.corejava.collections.ArrayListVsLinkedListVsArrayDequeDemo`.


## 4 ArrayList Gotchas

### Gotcha 1 — `Arrays.asList()` returns a fixed-size list
```java
// Arrays.asList looks like it creates an ArrayList — it does not
List<String> list = Arrays.asList("Alice", "Bob", "Carol");
list.add("Dave");  // ❌ UnsupportedOperationException!

// It returns a fixed-size backed by the original array
// You can set() existing elements but cannot add or remove

// ✅ Wrap in a real ArrayList if you need to modify it
List<String> mutable = new ArrayList<>(Arrays.asList("Alice","Bob"));

// ✅ Or use List.of() in Java 9+ — explicitly immutable
List<String> immutable = List.of("Alice", "Bob");  // clear intent
```

### Gotcha 2 — Autoboxing cost when using primitive values
```java
// ArrayList<Integer> boxes every int into an Integer object
List<Integer> numbers = new ArrayList<>();
for (int i = 0; i < 1_000_000; i++) {
    numbers.add(i);  // autoboxing: int → Integer — 1M objects created
}

// Each Integer is a separate heap object — 16 bytes minimum
// 1 million Integers = ~16MB just for the wrappers
// Plus pointer indirection for every access

// ✅ Use primitive collections for large numeric lists
// Eclipse Collections / Apache Commons / Trove
IntList numbers = IntLists.mutable.empty();
numbers.addAll(IntInterval.oneTo(1_000_000));  // no boxing
```

### Gotcha 3 — `subList()` is a view, not a copy
- `subList()` shares backing storage with the parent list.
- Changes in subList reflect in original list and vice versa.
- If you need independence, copy it: `new ArrayList<>(list.subList(from, to))`.

### Gotcha 4 — Removing elements in `for-each` (fail-fast)
- ArrayList iterators are **fail-fast**.
- If you structurally modify the list during enhanced for-loop iteration, it throws `ConcurrentModificationException`.

```java
List<String> names = new ArrayList<>(Arrays.asList("A", "B", "C"));
for (String name : names) {
    if ("B".equals(name)) {
        names.remove(name); // ❌ fail-fast -> ConcurrentModificationException
    }
}

// ✅ Solutions
names.removeIf("B"::equals); // option 1

Iterator<String> it = names.iterator(); // option 2
while (it.hasNext()) {
    if ("B".equals(it.next())) {
        it.remove();
    }
}
```

Runnable demo class: `com.gktechverse.corejava.collections.ArrayListGotchasDemo`.
