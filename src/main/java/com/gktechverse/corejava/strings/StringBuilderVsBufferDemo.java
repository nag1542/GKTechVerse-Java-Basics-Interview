package com.gktechverse.corejava.strings;

/**
 * Interview focus: StringBuilder vs StringBuffer.
 * StringBuilder is faster (not synchronized), StringBuffer is thread-safe.
 */
public class StringBuilderVsBufferDemo {
    public static void main(String[] args) {
        StringBuilder builder = new StringBuilder("Java");
        builder.append(" Builder");

        StringBuffer buffer = new StringBuffer("Java");
        buffer.append(" Buffer");

        System.out.println("=== StringBuilder vs StringBuffer Demo ===");
        System.out.println("StringBuilder output: " + builder);
        System.out.println("StringBuffer output: " + buffer);
    }
}
