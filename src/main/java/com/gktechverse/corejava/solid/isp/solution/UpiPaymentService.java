package com.gktechverse.corejava.solid.isp.solution;

public class UpiPaymentService implements Payment {

    @Override
    public void pay() {
        System.out.println("[UPI] Payment completed via UPI app.");
    }
}
