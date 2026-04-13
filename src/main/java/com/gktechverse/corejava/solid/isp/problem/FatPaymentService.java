package com.gktechverse.corejava.solid.isp.problem;

/**
 * ISP violation: one fat contract forces all clients to implement irrelevant operations.
 */
public interface FatPaymentService {
    void pay();
    void refund();
    void generateInvoice();
    void sendReceipt();
}
