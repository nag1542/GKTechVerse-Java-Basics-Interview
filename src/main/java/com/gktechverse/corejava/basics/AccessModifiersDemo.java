package com.gktechverse.corejava.basics;

/**
 * Interview focus: access modifiers.
 * Shows all four levels: private, default, protected, public.
 */
public class AccessModifiersDemo {
    private String privateMessage = "private: only inside this class";
    String defaultMessage = "default: same package only";
    protected String protectedMessage = "protected: package + child classes";
    public String publicMessage = "public: accessible everywhere";

    public static void main(String[] args) {
        AccessModifiersDemo demo = new AccessModifiersDemo();
        System.out.println("=== Access Modifiers Demo ===");
        System.out.println(demo.privateMessage);
        System.out.println(demo.defaultMessage);
        System.out.println(demo.protectedMessage);
        System.out.println(demo.publicMessage);
    }
}
