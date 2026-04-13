package com.gktechverse.corejava.solid.srp;

public class OrderPlacementService {
    private final OrderValidator orderValidator;
    private final PricingService pricingService;
    private final PaymentService paymentService;
    private final NotificationService notificationService;
    private final OrderRepository orderRepository;
    private final InventoryService inventoryService;

    public OrderPlacementService(OrderValidator orderValidator,
                                 PricingService pricingService,
                                 PaymentService paymentService,
                                 NotificationService notificationService,
                                 OrderRepository orderRepository,
                                 InventoryService inventoryService) {
        this.orderValidator = orderValidator;
        this.pricingService = pricingService;
        this.paymentService = paymentService;
        this.notificationService = notificationService;
        this.orderRepository = orderRepository;
        this.inventoryService = inventoryService;
    }

    public void placeOrder(Order order, Payment payment) {
        orderValidator.validate(order);
        double total = pricingService.calculateTotal(order);
        paymentService.charge(payment, total);
        orderRepository.save(order);
        inventoryService.reduceStock(order);
        notificationService.sendConfirmation(order.getCustomer(), order);
    }
}
