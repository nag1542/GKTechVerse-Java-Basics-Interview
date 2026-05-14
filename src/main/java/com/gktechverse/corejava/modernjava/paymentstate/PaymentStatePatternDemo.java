package com.gktechverse.corejava.modernjava.paymentstate;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * Real production-style example combining sealed hierarchy + records + switch patterns.
 */
public class PaymentStatePatternDemo {

    public static void main(String[] args) {
        List<PaymentState> states = List.of(
                new Pending("ORD-1001", new BigDecimal("249.99")),
                new Processing("ORD-1002", "GW-REF-7788", Instant.parse("2026-05-14T10:15:30Z")),
                new Completed("ORD-1003", "GW-REF-8899", Instant.parse("2026-05-14T10:20:00Z"), new BigDecimal("999.00")),
                new Failed("ORD-1004", "INSUFFICIENT_FUNDS", "Card balance too low"),
                new Failed("ORD-1005", "GATEWAY_TIMEOUT", "Gateway did not respond in time"),
                new Refunded("ORD-1006", new BigDecimal("120.00"), Instant.parse("2026-05-14T11:00:00Z"))
        );

        System.out.println("=== Payment State Pattern Demo ===");
        for (PaymentState state : states) {
            System.out.println(buildStatusMessage(state));
        }
    }

    static String buildStatusMessage(PaymentState state) {
        return switch (state) {
            case Pending p ->
                    "Awaiting payment of " + p.amount() + " for " + p.orderId();

            case Processing p ->
                    "Processing since " + p.startedAt() + " (ref: " + p.gatewayRef() + ")";

            case Completed c ->
                    "Paid " + c.charged() + " at " + c.completedAt();

            case Failed f when f.errorCode().equals("INSUFFICIENT_FUNDS") ->
                    "Payment declined — insufficient funds";

            case Failed f ->
                    "Payment failed: " + f.reason() + " [" + f.errorCode() + "]";

            case Refunded r ->
                    "Refunded " + r.refundAmount() + " at " + r.refundedAt();
        };
    }

    sealed interface PaymentState
            permits Pending, Processing, Completed, Failed, Refunded { }

    record Pending(String orderId, BigDecimal amount) implements PaymentState { }

    record Processing(String orderId, String gatewayRef, Instant startedAt) implements PaymentState { }

    record Completed(String orderId, String gatewayRef, Instant completedAt, BigDecimal charged) implements PaymentState { }

    record Failed(String orderId, String errorCode, String reason) implements PaymentState { }

    record Refunded(String orderId, BigDecimal refundAmount, Instant refundedAt) implements PaymentState { }
}
