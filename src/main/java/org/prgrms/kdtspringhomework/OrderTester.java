package org.prgrms.kdtspringhomework;

import org.prgrms.kdtspringhomework.config.AppConfiguration;
import org.prgrms.kdtspringhomework.order.domain.OrderItem;
import org.prgrms.kdtspringhomework.order.service.OrderService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {
    public static void main(String[] args) {

        var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        var customerId = UUID.randomUUID();
        var orderService = applicationContext.getBean(OrderService.class);

        var order = orderService.createOrder(customerId, new ArrayList<OrderItem>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }});

        Assert.isTrue(order.totalAmount() == 100L, String.format("totalDiscountAmount {0} is not 100L", order.totalAmount()));
    }
}