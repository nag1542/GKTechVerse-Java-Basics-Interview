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
