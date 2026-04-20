package com.gktechverse.corejava.oops.interfacevsabstract;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Base64;
import java.util.UUID;

/**
 * Interview focus: when to use interface vs abstract class.
 * Interfaces model capabilities; abstract classes share state and template behavior.
 */
public class InterfaceVsAbstractWhenToUseDemo {

    interface Exportable {
        byte[] toBytes();

        default String toBase64() {
            return Base64.getEncoder().encodeToString(toBytes());
        }
    }

    interface Auditable {
        LocalDateTime getCreatedAt();

        String getCreatedBy();
    }

    static class Order implements Exportable, Auditable {
        private final String orderId;
        private final LocalDateTime createdAt;
        private final String createdBy;

        Order(String orderId, LocalDateTime createdAt, String createdBy) {
            this.orderId = orderId;
            this.createdAt = createdAt;
            this.createdBy = createdBy;
        }

        @Override
        public byte[] toBytes() {
            return ("orderId=" + orderId + ",createdBy=" + createdBy).getBytes(StandardCharsets.UTF_8);
        }

        @Override
        public LocalDateTime getCreatedAt() {
            return createdAt;
        }

        @Override
        public String getCreatedBy() {
            return createdBy;
        }
    }

    abstract static class BaseEntity {
        private final UUID id;
        private final Instant createdAt;

        protected BaseEntity() {
            this.id = UUID.randomUUID();
            this.createdAt = Instant.now();
        }

        public UUID getId() {
            return id;
        }

        public Instant getCreatedAt() {
            return createdAt;
        }

        public final String describe() {
            return getClass().getSimpleName() + "[" + toDetails() + "]";
        }

        protected abstract String toDetails();
    }

    static class User extends BaseEntity {
        private final String email;

        User(String email) {
            this.email = email;
        }

        @Override
        protected String toDetails() {
            return "email=" + email;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Interface vs Abstract: When To Use ===");

        Order order = new Order("ORD-101", LocalDateTime.of(2026, 4, 20, 9, 30), "admin");
        System.out.println("Order created at: " + order.getCreatedAt());
        System.out.println("Order created by: " + order.getCreatedBy());
        System.out.println("Order export (Base64): " + order.toBase64());

        User user = new User("user@gktechverse.com");
        System.out.println("User description: " + user.describe());
        System.out.println("User id: " + user.getId());
        System.out.println("User created at (UTC): " + user.getCreatedAt().atOffset(ZoneOffset.UTC));
    }
}
