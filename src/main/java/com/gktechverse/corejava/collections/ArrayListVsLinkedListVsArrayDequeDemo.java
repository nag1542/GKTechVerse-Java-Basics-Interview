package com.gktechverse.corejava.collections;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Comparison demo: ArrayList vs LinkedList vs ArrayDeque with real-world scenarios.
 */
public class ArrayListVsLinkedListVsArrayDequeDemo {

    public static void main(String[] args) {
        System.out.println("=== ArrayList vs LinkedList vs ArrayDeque ===");
        compareInternalsAndComplexity();
        realWorldExamples();
        whenToUseWhat();
    }

    private static void compareInternalsAndComplexity() {
        System.out.println("\n1) Quick comparison");
        System.out.println("ArrayList  : dynamic array, O(1) random access, O(n) middle insert/remove");
        System.out.println("LinkedList : doubly-linked nodes, O(n) random access, O(1) add/remove at ends");
        System.out.println("ArrayDeque : resizable circular array, O(1) add/remove at both ends, no random index API");
    }

    private static void realWorldExamples() {
        System.out.println("\n2) Real-world examples");

        List<String> productSearchResults = new ArrayList<>();
        productSearchResults.add("Laptop");
        productSearchResults.add("Mouse");
        productSearchResults.add("Keyboard");
        System.out.println("ArrayList example (read-heavy product catalog page): " + productSearchResults);

        LinkedList<String> approvalWorkflow = new LinkedList<>();
        approvalWorkflow.addLast("SUBMITTED");
        approvalWorkflow.addLast("MANAGER_APPROVAL");
        approvalWorkflow.addLast("FINANCE_APPROVAL");
        approvalWorkflow.removeFirst();
        System.out.println("LinkedList example (approval steps removed from front): " + approvalWorkflow);

        Queue<String> orderProcessingQueue = new ArrayDeque<>();
        orderProcessingQueue.offer("ORD-1001");
        orderProcessingQueue.offer("ORD-1002");
        orderProcessingQueue.offer("ORD-1003");
        orderProcessingQueue.poll();
        System.out.println("ArrayDeque example (high-throughput queue): " + orderProcessingQueue);
    }

    private static void whenToUseWhat() {
        System.out.println("\n3) When to use what");
        System.out.println("Use ArrayList when reads/index-based access dominate and appends are common.");
        System.out.println("Use LinkedList when frequent inserts/removes at ends are needed plus list semantics.");
        System.out.println("Use ArrayDeque for stack/queue/deque workloads; usually faster and leaner than LinkedList for queue operations.");
    }
}
