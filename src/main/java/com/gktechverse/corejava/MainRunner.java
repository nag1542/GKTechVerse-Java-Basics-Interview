package com.gktechverse.corejava;

import com.gktechverse.corejava.basics.AccessModifiersDemo;
import com.gktechverse.corejava.basics.DataTypesDemo;
import com.gktechverse.corejava.basics.TypeCastingDemo;
import com.gktechverse.corejava.basics.VariablesDemo;
import com.gktechverse.corejava.collections.CollectionsEcommerceSelectionDemo;
import com.gktechverse.corejava.collections.CollectionsFrameworkInterviewDemo;
import com.gktechverse.corejava.collections.ArrayListInternalWorkingDemo;
import com.gktechverse.corejava.collections.ArrayListMemoryOptimizationPatternsDemo;
import com.gktechverse.corejava.collections.ArrayListVsLinkedListVsArrayDequeDemo;
import com.gktechverse.corejava.collections.ArrayListGotchasDemo;
import com.gktechverse.corejava.collections.hashmap.HashMapTeachingSeriesRunner;
import com.gktechverse.corejava.exceptions.CheckedExceptionHandlingOptionsDemo;
import com.gktechverse.corejava.exceptions.CheckedVsUncheckedDemo;
import com.gktechverse.corejava.exceptions.CustomExceptionDemo;
import com.gktechverse.corejava.exceptions.ExceptionHandlingDeepDiveDemo;
import com.gktechverse.corejava.exceptions.ExceptionAntiPatternsDemo;
import com.gktechverse.corejava.exceptions.TryCatchFinallyDemo;
import com.gktechverse.corejava.exceptions.MultiCatchFinallyAndChainingDemo;
import com.gktechverse.corejava.exceptions.TryWithResourcesInternalsDemo;
import com.gktechverse.corejava.jvm.BoxingUnboxingDemo;
import com.gktechverse.corejava.jvm.ClassLoadingDemo;
import com.gktechverse.corejava.jvm.MemoryAreasDemo;
import com.gktechverse.corejava.modernjava.records.RecordsPointDemo;
import com.gktechverse.corejava.modernjava.sealedclasses.SealedClassesDemo;
import com.gktechverse.corejava.modernjava.sealedrecords.SealedRecordsResultDemo;
import com.gktechverse.corejava.modernjava.patternmatching.PatternMatchingDemo;
import com.gktechverse.corejava.modernjava.switchpatternmatching.SwitchPatternMatchingDemo;
import com.gktechverse.corejava.multithreading.BankingTransferConsistencyAndDeadlockDemo;
import com.gktechverse.corejava.multithreading.DeadlockEnterpriseUseCasesDemo;
import com.gktechverse.corejava.multithreading.MultithreadingEnterpriseVideoRunner;
import com.gktechverse.corejava.designpatterns.singleton.SingletonPatternsDemo;
import com.gktechverse.corejava.oops.AbstractionDemo;
import com.gktechverse.corejava.oops.AssociationAggregationCompositionDemo;
import com.gktechverse.corejava.oops.ClassObjectDemo;
import com.gktechverse.corejava.oops.ConstructorDemo;
import com.gktechverse.corejava.oops.EncapsulationDemo;
import com.gktechverse.corejava.oops.FinalKeywordDemo;
import com.gktechverse.corejava.oops.InheritanceDemo;
import com.gktechverse.corejava.oops.InterfaceVsAbstractDemo;
import com.gktechverse.corejava.oops.MethodHidingDemo;
import com.gktechverse.corejava.oops.PolymorphismDemo;
import com.gktechverse.corejava.oops.SuperKeywordDemo;
import com.gktechverse.corejava.oops.ThisKeywordDemo;
import com.gktechverse.corejava.staticmemory.HeapStackAndObjectCreationDemo;
import com.gktechverse.corejava.streams.StreamsInterviewRunner;
import com.gktechverse.corejava.staticmemory.SingletonVsStaticDemo;
import com.gktechverse.corejava.staticmemory.StaticBlockVsVariableDemo;
import com.gktechverse.corejava.staticmemory.StaticKeywordDemo;
import com.gktechverse.corejava.staticmemory.StaticMembersAndBlockDemo;
import com.gktechverse.corejava.strings.EqualsVsDoubleEqualsDemo;
import com.gktechverse.corejava.strings.ImmutabilityDemo;
import com.gktechverse.corejava.strings.StringBuilderVsBufferDemo;
import com.gktechverse.corejava.strings.StringPoolDemo;

import java.util.Scanner;

/**
 * Console menu runner for beginners.
 * Lets users pick a topic and run all demos in that topic.
 */
public class MainRunner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n=== Crack Core Java Interviews - Beginner ===");
            System.out.println("1. Basics");
            System.out.println("2. JVM");
            System.out.println("3. OOPs");
            System.out.println("4. Exceptions");
            System.out.println("5. Strings");
            System.out.println("6. Collections Framework");
            System.out.println("7. Static & Memory Concepts");
            System.out.println("8. Multithreading (Enterprise + Deadlock + CompletableFuture)");
            System.out.println("9. Streams (Loop vs Stream, Lazy Evaluation, Patterns)");
            System.out.println("10. Design Patterns (Singleton)");
            System.out.println("11. Modern Java Features (Records + Sealed)");
            System.out.println("0. Exit");
            System.out.print("Select a topic: ");

            String choice = scanner.nextLine();
            System.out.println();
            switch (choice) {
                case "1" -> runBasics();
                case "2" -> runJvm();
                case "3" -> runOops();
                case "4" -> runExceptions();
                case "5" -> runStrings();
                case "6" -> runCollections();
                case "7" -> runStaticAndMemory();
                case "8" -> runMultithreading();
                case "9" -> runStreams();
                case "10" -> runDesignPatterns();
                case "11" -> runModernJavaFeatures();
                case "0" -> {
                    System.out.println("Thanks for practicing Core Java. Keep revising!");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Please select from the menu.");
            }
        }
    }

    private static void runBasics() {
        VariablesDemo.main(new String[]{});
        DataTypesDemo.main(new String[]{});
        TypeCastingDemo.main(new String[]{});
        AccessModifiersDemo.main(new String[]{});
    }

    private static void runJvm() {
        MemoryAreasDemo.main(new String[]{});
        ClassLoadingDemo.main(new String[]{});
        BoxingUnboxingDemo.main(new String[]{});
    }

    private static void runOops() {
        ClassObjectDemo.main(new String[]{});
        EncapsulationDemo.main(new String[]{});
        AbstractionDemo.main(new String[]{});
        InheritanceDemo.main(new String[]{});
        PolymorphismDemo.main(new String[]{});
        ConstructorDemo.main(new String[]{});
        InterfaceVsAbstractDemo.main(new String[]{});
        FinalKeywordDemo.main(new String[]{});
        AssociationAggregationCompositionDemo.main(new String[]{});
        ThisKeywordDemo.main(new String[]{});
        SuperKeywordDemo.main(new String[]{});
        MethodHidingDemo.main(new String[]{});
    }

    private static void runExceptions() {
        CheckedVsUncheckedDemo.main(new String[]{});
        TryCatchFinallyDemo.main(new String[]{});
        CustomExceptionDemo.main(new String[]{});
        ExceptionHandlingDeepDiveDemo.main(new String[]{});
        CheckedExceptionHandlingOptionsDemo.main(new String[]{});
        TryWithResourcesInternalsDemo.main(new String[]{});
        MultiCatchFinallyAndChainingDemo.main(new String[]{});
        ExceptionAntiPatternsDemo.main(new String[]{});
    }

    private static void runStrings() {
        StringPoolDemo.main(new String[]{});
        EqualsVsDoubleEqualsDemo.main(new String[]{});
        StringBuilderVsBufferDemo.main(new String[]{});
        ImmutabilityDemo.main(new String[]{});
    }

    private static void runCollections() {
        CollectionsFrameworkInterviewDemo.main(new String[]{});
        CollectionsEcommerceSelectionDemo.main(new String[]{});
        HashMapTeachingSeriesRunner.main(new String[]{});
        ArrayListInternalWorkingDemo.main(new String[]{});
        ArrayListMemoryOptimizationPatternsDemo.main(new String[]{});
        ArrayListVsLinkedListVsArrayDequeDemo.main(new String[]{});
        ArrayListGotchasDemo.main(new String[]{});
    }

    private static void runStaticAndMemory() {
        StaticKeywordDemo.main(new String[]{});
        StaticMembersAndBlockDemo.main(new String[]{});
        StaticBlockVsVariableDemo.main(new String[]{});
        SingletonVsStaticDemo.main(new String[]{});
        HeapStackAndObjectCreationDemo.main(new String[]{});
    }

    private static void runMultithreading() {
        MultithreadingEnterpriseVideoRunner.main(new String[]{});
        DeadlockEnterpriseUseCasesDemo.main(new String[]{});
        BankingTransferConsistencyAndDeadlockDemo.main(new String[]{});
    }

    private static void runStreams() {
        StreamsInterviewRunner.main(new String[]{});
    }

    private static void runDesignPatterns() {
        SingletonPatternsDemo.main(new String[]{});
    }

    private static void runModernJavaFeatures() {
        RecordsPointDemo.main(new String[]{});
        SealedClassesDemo.main(new String[]{});
        SealedRecordsResultDemo.main(new String[]{});
        PatternMatchingDemo.main(new String[]{});
        SwitchPatternMatchingDemo.main(new String[]{});
    }
}
