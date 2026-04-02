package com.gktechverse.corejava.streamsdemo;

import java.util.List;
import java.util.stream.Stream;

public class LoopVsStreamDemo {
	public static void main(String[] args) {
		
		List<String> names = List.of("Alice", "Bob", "Anna", "Charlie", "Amy");
		System.out.println("--- Creating stream ---");

		Stream<String> stream = names.stream()
		    .filter(name -> {
		        System.out.println("  filter: " + name);
		        return name.startsWith("A");
		    })
		    .map(name -> {
		        System.out.println("  map:    " + name);
		        return name.toUpperCase();
		    });

	}
}
