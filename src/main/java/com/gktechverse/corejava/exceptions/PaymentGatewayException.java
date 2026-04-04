package com.gktechverse.corejava.exceptions;

/**
 * Specific payment failure for gateway/network provider outages.
 */
public class PaymentGatewayException extends PaymentException {
    public PaymentGatewayException(String message, Throwable cause) {
        super(message, "PAYMENT_002", cause);
    }
}
