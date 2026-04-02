package com.gktechverse.corejava.collections;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Practical, interview-oriented comparison of Java Collections using e-commerce orders/products.
 */
public class CollectionsEcommerceSelectionDemo {

    public static void main(String[] args) {
        System.out.println("=== Java Collections in E-commerce (When to use what) ===");
        listDemo();
        setDemo();
        mapDemo();
        queueDemo();
        iteratorDemo();
    }

    private static void listDemo() {
        System.out.println("\n1) List: ArrayList vs LinkedList");

        List<OrderItem> cartItems = new ArrayList<>();
        cartItems.add(new OrderItem("P-100", 1));
        cartItems.add(new OrderItem("P-200", 2));
        cartItems.add(new OrderItem("P-300", 1));

        LinkedList<String> packingQueue = new LinkedList<>();
        packingQueue.add("ORD-101");
        packingQueue.add("ORD-102");
        packingQueue.addFirst("ORD-100-EXPRESS");

        System.out.println("ArrayList -> best default for read-heavy indexed operations. Cart items: " + cartItems);
        System.out.println("LinkedList -> better for frequent add/remove at ends. Packing queue: " + packingQueue);
        System.out.println("Choose ArrayList for product listing/cart. Choose LinkedList for queue-like workflow.");
    }

    private static void setDemo() {
        System.out.println("\n2) Set: HashSet vs LinkedHashSet vs TreeSet");

        Set<String> purchasedSkusHashSet = new HashSet<>();
        purchasedSkusHashSet.add("SKU-300");
        purchasedSkusHashSet.add("SKU-100");
        purchasedSkusHashSet.add("SKU-100");

        Set<String> recentlyViewedSkus = new LinkedHashSet<>();
        recentlyViewedSkus.add("SKU-200");
        recentlyViewedSkus.add("SKU-110");
        recentlyViewedSkus.add("SKU-330");
        recentlyViewedSkus.add("SKU-110");

        Set<Integer> sortedDiscountRates = new TreeSet<>();
        sortedDiscountRates.add(20);
        sortedDiscountRates.add(5);
        sortedDiscountRates.add(15);
        sortedDiscountRates.add(5);

        System.out.println("HashSet -> unique, no order guarantee: " + purchasedSkusHashSet);
        System.out.println("LinkedHashSet -> unique + insertion order: " + recentlyViewedSkus);
        System.out.println("TreeSet -> unique + sorted order: " + sortedDiscountRates);
        System.out.println("Choose HashSet for fastest uniqueness checks, LinkedHashSet for stable user-facing order, TreeSet for sorted outputs.");
    }

    private static void mapDemo() {
        System.out.println("\n3) Map: HashMap vs LinkedHashMap vs TreeMap");

        Map<String, Product> productById = new HashMap<>();
        productById.put("P-100", new Product("P-100", "Laptop Sleeve", 999));
        productById.put("P-200", new Product("P-200", "Mechanical Keyboard", 4599));

        Map<String, Instant> recentlyAccessedOrders = new LinkedHashMap<>();
        recentlyAccessedOrders.put("ORD-101", Instant.parse("2026-03-24T10:15:30Z"));
        recentlyAccessedOrders.put("ORD-103", Instant.parse("2026-03-24T10:17:00Z"));
        recentlyAccessedOrders.put("ORD-102", Instant.parse("2026-03-24T10:20:00Z"));

        TreeMap<Integer, String> priceBand = new TreeMap<>();
        priceBand.put(499, "BUDGET");
        priceBand.put(1999, "VALUE");
        priceBand.put(4999, "PREMIUM");

        System.out.println("HashMap -> best default for key-based lookup. P-200 = " + productById.get("P-200"));
        System.out.println("LinkedHashMap -> preserves insertion order: " + recentlyAccessedOrders.keySet());
        System.out.println("TreeMap -> sorted keys + range queries. Ceiling band for 2200 = " + priceBand.ceilingEntry(2200));
        System.out.println("Choose HashMap for id lookup, LinkedHashMap for predictable iteration/LRU style, TreeMap for sorted/range logic.");
    }

    private static void queueDemo() {
        System.out.println("\n4) Queue & PriorityQueue");

        Queue<String> orderEventQueue = new LinkedList<>();
        orderEventQueue.offer("ORDER_PLACED");
        orderEventQueue.offer("PAYMENT_CONFIRMED");
        orderEventQueue.offer("PACKING_STARTED");

        PriorityQueue<OrderTask> dispatchPriorityQueue = new PriorityQueue<>(Comparator.comparingInt(OrderTask::priority));
        dispatchPriorityQueue.offer(new OrderTask("ORD-700", 3));
        dispatchPriorityQueue.offer(new OrderTask("ORD-701", 1));
        dispatchPriorityQueue.offer(new OrderTask("ORD-702", 2));

        System.out.println("Queue (FIFO) poll order: " + orderEventQueue.poll() + " -> " + orderEventQueue.poll());
        System.out.print("PriorityQueue (lowest priority number first) dispatch order: ");
        while (!dispatchPriorityQueue.isEmpty()) {
            System.out.print(dispatchPriorityQueue.poll().orderId() + " ");
        }
        System.out.println();
        System.out.println("Choose Queue for arrival order workflows; choose PriorityQueue for SLA/risk/urgency driven processing.");
    }

    private static void iteratorDemo() {
        System.out.println("\n5) Iterators: Fail-Fast vs Fail-Safe");

        List<String> activeOrderIds = new ArrayList<>(List.of("ORD-1", "ORD-2", "ORD-3"));
        try {
            for (String orderId : activeOrderIds) {
                if ("ORD-2".equals(orderId)) {
                    activeOrderIds.add("ORD-4");
                }
            }
        } catch (ConcurrentModificationException exception) {
            System.out.println("Fail-fast iterator detected concurrent modification: " + exception.getClass().getSimpleName());
        }

        List<String> failSafeView = new CopyOnWriteArrayList<>(List.of("P-1", "P-2", "P-3"));
        for (String productId : failSafeView) {
            if ("P-2".equals(productId)) {
                failSafeView.add("P-4");
            }
        }

        System.out.println("Fail-safe style (CopyOnWriteArrayList) iterates safely, final values: " + failSafeView);

        List<String> cancellableOrders = new ArrayList<>(List.of("ORD-10", "ORD-11", "ORD-12"));
        Iterator<String> iterator = cancellableOrders.iterator();
        while (iterator.hasNext()) {
            if ("ORD-11".equals(iterator.next())) {
                iterator.remove();
            }
        }
        System.out.println("Correct fail-fast removal via Iterator.remove(): " + cancellableOrders);
    }

    private record Product(String productId, String name, int priceInInr) {
    }

    private record OrderTask(String orderId, int priority) {
    }

    private record OrderItem(String productId, int quantity) {
    }
}
