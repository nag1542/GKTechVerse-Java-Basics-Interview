package com.gktechverse.corejava.exceptions;

import java.math.BigDecimal;

/**
 * Specific payment failure when user balance is insufficient.
 */
public class InsufficientFundsException extends PaymentException {
    private final BigDecimal required;
    private final BigDecimal available;

    public InsufficientFundsException(BigDecimal required, BigDecimal available) {
        super("Insufficient funds: need " + required + " have " + available, "PAYMENT_001");
        this.required = required;
        this.available = available;
    }

    public BigDecimal getRequired() {
        return required;
    }

    public BigDecimal getAvailable() {
        return available;
    }
}
