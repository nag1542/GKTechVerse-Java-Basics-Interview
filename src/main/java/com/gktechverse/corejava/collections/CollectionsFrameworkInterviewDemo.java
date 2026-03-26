package com.gktechverse.corejava.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Interview focus: Collections Framework basics explained with enterprise-style scenarios.
 */
public class CollectionsFrameworkInterviewDemo {

    public static void main(String[] args) {
        System.out.println("=== Collections Framework (Basic & Real Interview Focus) ===");
        explainCollectionAndFramework();
        arrayVsArrayList();
        listVsSet();
        arrayListVsLinkedList();
        hashMapBasicsAndOperations();
        hashMapVsHashtable();
        hashSetVsTreeSet();
        iteratorExplanation();
        hashMapInternalWorking();
        equalsVsHashCode();
        overridingEqualsAndHashCodeTogether();
        sameHashCodeBehavior();
        streamVsLoopsUseCases();
    }

    private static void explainCollectionAndFramework() {
        System.out.println("\n1) What is a Collection in Java?");
        System.out.println("Collection is an object that groups multiple elements (orders, users, payments) into a single unit.");

        System.out.println("\n2) What is the Collections Framework?");
        System.out.println("It is a set of interfaces (List, Set, Map), implementations (ArrayList, HashMap, HashSet), and utility algorithms.");
        System.out.println("Enterprise use case: OMS (Order Management System) can use List for order queue, Set for unique SKU codes, Map for orderId -> order object.");
    }

    private static void arrayVsArrayList() {
        System.out.println("\n3) Array vs ArrayList");

        String[] fixedMonthlyRegions = {"APAC", "EMEA", "US"};
        List<String> dynamicRegions = new ArrayList<>(Arrays.asList("APAC", "EMEA", "US"));
        dynamicRegions.add("LATAM");

        System.out.println("Array size is fixed after creation. Example fixed monthly regions count: " + fixedMonthlyRegions.length);
        System.out.println("ArrayList can grow/shrink dynamically. Example active regions after expansion: " + dynamicRegions);
        System.out.println("Array stores primitives directly; ArrayList stores objects and offers methods like add/remove/contains.");
    }

    private static void listVsSet() {
        System.out.println("\n4) List vs Set");
        List<String> retryEventsInOrder = new ArrayList<>(Arrays.asList("PAYMENT_TIMEOUT", "PAYMENT_TIMEOUT", "PAYMENT_SUCCESS"));
        Set<String> uniqueEventTypes = new LinkedHashSet<>(retryEventsInOrder);

        System.out.println("List allows duplicates + preserves insertion order. Retry event log: " + retryEventsInOrder);
        System.out.println("Set stores unique values only. Unique event types for dashboard: " + uniqueEventTypes);
    }

    private static void arrayListVsLinkedList() {
        System.out.println("\n5) ArrayList vs LinkedList");
        List<String> activeUsersCache = new ArrayList<>(Arrays.asList("u100", "u101", "u102"));
        LinkedList<String> supportTicketQueue = new LinkedList<>(Arrays.asList("T-1", "T-2", "T-3"));
        supportTicketQueue.addFirst("T-0-CRITICAL");

        System.out.println("ArrayList: fast random read by index. Good for frequently read data like active users cache.");
        System.out.println("LinkedList: efficient add/remove at ends. Good for queue-like processing: " + supportTicketQueue);
        System.out.println("ArrayList snapshot: " + activeUsersCache);
    }

    private static void hashMapBasicsAndOperations() {
        System.out.println("\n6) What is HashMap? Basic operations");
        Map<String, String> orderStatusByOrderId = new HashMap<>();

        orderStatusByOrderId.put("ORD-101", "CREATED"); // add
        orderStatusByOrderId.put("ORD-102", "PAYMENT_PENDING"); // add
        orderStatusByOrderId.put("ORD-101", "PAID"); // update

        String status = orderStatusByOrderId.get("ORD-101"); // read
        orderStatusByOrderId.remove("ORD-102"); // delete

        System.out.println("HashMap stores key-value pairs for fast lookup. Example orderId -> status map: " + orderStatusByOrderId);
        System.out.println("Fetched status for ORD-101: " + status);
        System.out.println("containsKey(ORD-102): " + orderStatusByOrderId.containsKey("ORD-102"));
    }

    private static void hashMapVsHashtable() {
        System.out.println("\n7) HashMap vs Hashtable");
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put(null, "Allowed in HashMap");

        Hashtable<String, String> hashtable = new Hashtable<>();
        hashtable.put("config.source", "vault");

        System.out.println("HashMap is not synchronized and allows one null key + multiple null values.");
        System.out.println("Hashtable is synchronized (legacy) and does not allow null key/value.");
        System.out.println("Modern enterprise code prefers HashMap + ConcurrentHashMap based on concurrency needs.");
    }

    private static void hashSetVsTreeSet() {
        System.out.println("\n8) HashSet vs TreeSet");
        Set<String> uniqueOrderIds = new HashSet<>(Arrays.asList("ORD-200", "ORD-101", "ORD-310"));
        Set<String> sortedOrderIds = new TreeSet<>(uniqueOrderIds);

        System.out.println("HashSet: unique elements, no guaranteed order. Used when order is not required: " + uniqueOrderIds);
        System.out.println("TreeSet: unique + sorted order. Useful for sorted reporting/export: " + sortedOrderIds);
    }

    private static void iteratorExplanation() {
        System.out.println("\n9) What is an Iterator?");
        List<String> jobStages = new ArrayList<>(Arrays.asList("FETCH", "VALIDATE", "PERSIST"));
        Iterator<String> iterator = jobStages.iterator();

        System.out.print("Iterator enables safe sequential traversal: ");
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();
    }

    private static void hashMapInternalWorking() {
        System.out.println("\n10) How does HashMap work internally? (basic)");
        System.out.println("HashMap computes hashCode of key -> finds bucket index -> stores/retrieves entry.");
        System.out.println("If multiple keys land in same bucket (collision), entries are chained and searched by equals().");
        System.out.println("In enterprise apps this gives average O(1) get/put when hash distribution is good.");
    }

    private static void equalsVsHashCode() {
        System.out.println("\n11) equals() vs hashCode()");
        CustomerKey c1 = new CustomerKey("tenant-a", "CUST-9001");
        CustomerKey c2 = new CustomerKey("tenant-a", "CUST-9001");

        System.out.println("equals() checks logical equality, hashCode() is used for bucket placement.");
        System.out.println("c1.equals(c2): " + c1.equals(c2));
        System.out.println("c1.hashCode() == c2.hashCode(): " + (c1.hashCode() == c2.hashCode()));
    }

    private static void overridingEqualsAndHashCodeTogether() {
        System.out.println("\n12) Why override equals() and hashCode() together?");
        Set<CustomerKey> customerSet = new HashSet<>();
        customerSet.add(new CustomerKey("tenant-a", "CUST-9001"));
        customerSet.add(new CustomerKey("tenant-a", "CUST-9001"));

        System.out.println("When both are overridden consistently, HashSet/HashMap can correctly identify duplicates.");
        System.out.println("Unique customer keys count (expected 1): " + customerSet.size());
    }

    private static void sameHashCodeBehavior() {
        System.out.println("\n13) What happens if two objects have same hashCode?");
        CustomerKey key1 = new CustomerKey("tenant-a", "COLLIDE-1");
        CustomerKey key2 = new CustomerKey("tenant-b", "COLLIDE-2");

        Map<CustomerKey, String> customerTier = new HashMap<>();
        customerTier.put(key1, "GOLD");
        customerTier.put(key2, "SILVER");

        System.out.println("Same hashCode does not mean objects are equal. HashMap handles collision using equals() checks in the bucket.");
        System.out.println("Retrieved tier for key1: " + customerTier.get(key1));
        System.out.println("Retrieved tier for key2: " + customerTier.get(key2));
    }

    private static void streamVsLoopsUseCases() {
        System.out.println("\n14) Stream vs Loop: when should we use what?");

        List<Integer> invoiceAmounts = Arrays.asList(1200, 900, 2500, 400, 1800, 3000);

        List<Integer> highValueInvoicesUsingLoop = new ArrayList<>();
        for (Integer amount : invoiceAmounts) {
            if (amount >= 1500) {
                highValueInvoicesUsingLoop.add(amount);
            }
        }

        List<Integer> highValueInvoicesUsingStream = invoiceAmounts.stream()
                .filter(amount -> amount >= 1500)
                .collect(Collectors.toList());

        int sumWithLoop = 0;
        for (Integer amount : invoiceAmounts) {
            sumWithLoop += amount;
        }

        int sumWithStream = invoiceAmounts.stream().reduce(0, Integer::sum);

        System.out.println("Data set (invoice amounts): " + invoiceAmounts);
        System.out.println("Loop result (>= 1500): " + highValueInvoicesUsingLoop);
        System.out.println("Stream result (>= 1500): " + highValueInvoicesUsingStream);
        System.out.println("Total by loop: " + sumWithLoop + ", total by stream: " + sumWithStream);

        System.out.println("\nWhen to prefer Streams:");
        System.out.println("- For data transformation pipelines (filter -> map -> sort -> collect).\n"
                + "  Example: convert orders into DTOs for API response in a single readable flow.");
        System.out.println("- For aggregate operations (count, max, average, grouping).\n"
                + "  Example: dashboard metrics from transaction lists.");
        System.out.println("- For declarative style where 'what to do' is clearer than 'how to iterate'.");

        System.out.println("\nWhen to prefer Loops:");
        System.out.println("- When you need index-based logic (previous/next comparison, in-place update).\n"
                + "  Example: compare current day sales with previous day in an array.");
        System.out.println("- When early break/continue is required for imperative control flow.\n"
                + "  Example: stop scanning as soon as a fraud transaction is found.");
        System.out.println("- When debugging step-by-step mutation is easier with explicit state variables.");

        System.out.println("\nInterview takeaway:");
        System.out.println("Use Streams for readable bulk operations on collections; "
                + "use loops for low-level control, complex branching, and index-sensitive logic.");
    }

    /**
     * Example enterprise key object used in cache keys / map keys.
     */
    private static final class CustomerKey {
        private final String tenantId;
        private final String customerId;

        private CustomerKey(String tenantId, String customerId) {
            this.tenantId = tenantId;
            this.customerId = customerId;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!(object instanceof CustomerKey that)) {
                return false;
            }
            return tenantId.equals(that.tenantId) && customerId.equals(that.customerId);
        }

        @Override
        public int hashCode() {
            if (customerId.startsWith("COLLIDE")) {
                return 42;
            }
            return 31 * tenantId.hashCode() + customerId.hashCode();
        }

        @Override
        public String toString() {
            return tenantId + ":" + customerId;
        }
    }
}
