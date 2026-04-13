package com.gktechverse.corejava.solid.srp;

public class Payment {
    private final String method;
    private final String reference;

    public Payment(String method, String reference) {
        this.method = method;
        this.reference = reference;
    }

    public String getMethod() {
        return method;
    }

    public String getReference() {
        return reference;
    }
}
