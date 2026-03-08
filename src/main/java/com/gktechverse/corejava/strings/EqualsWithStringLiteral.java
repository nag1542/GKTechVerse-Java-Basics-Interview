package com.gktechverse.corejava.strings;

public class EqualsWithStringLiteral {
	public static void main(String[] args) {
		String s1 = "GK";
		String s2 = "GK";
		System.out.println("=== equals() vs == Demo ===");
		System.out.println("a == b: " + (s1 == s2));
		System.out.println("a.equals(b): " + s1.equals(s2));
		
		/*
		 * S1 and S2 refering same object inside stringpool
		 */
	}
}
