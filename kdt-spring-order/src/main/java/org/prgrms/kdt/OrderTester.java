package org.prgrms.kdt;

import org.prgrms.kdt.config.AppConfiguration;
import org.prgrms.kdt.domain.order.Order;
import org.prgrms.kdt.domain.order.OrderItem;
import org.prgrms.kdt.domain.order.OrderProperties;
import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.repository.VoucherRepository;
import org.prgrms.kdt.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderTester {

    private static final Logger logger = LoggerFactory.getLogger(OrderTester.class);

    public static void main(String[] args) throws IOException {
        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

        OrderProperties orderProperties = applicationContext.getBean(OrderProperties.class);
        logger.warn("logger name ==>{}", logger.getName());
        logger.error("version -> {}", orderProperties.getVersion());
        logger.warn("minimumOrderAmount -> {}", orderProperties.getMinimumOrderAmount());
        logger.warn("supportVendors -> {}", orderProperties.getSupportVendors());
        logger.warn("description -> {}", orderProperties.getDescription());

        Resource resource = applicationContext.getResource("classpath:application.yml");
        Resource resource2 = applicationContext.getResource("file:sample.txt");
        Resource resource3 = applicationContext.getResource("https://stackoverflow.com/");

        ReadableByteChannel readableByteChannel = Channels.newChannel(resource3.getURL().openStream());
        BufferedReader bufferedReader = new BufferedReader(Channels.newReader(readableByteChannel, StandardCharsets.UTF_8));
        String collect = bufferedReader.lines().collect(Collectors.joining("\n"));
//        System.out.println("collect = " + collect);

//        System.out.println("resource = " + resource2.getClass().getCanonicalName());
        File file = resource2.getFile();
        List<String> strings = Files.readAllLines(file.toPath());
//        System.out.println("strings = " + strings.stream().reduce("", (a, b) -> a + "\n" + b));

        ArrayList<OrderItem> orderItems = new ArrayList<>(Arrays.asList(new OrderItem(UUID.randomUUID(), 100, 1)));
        UUID customerId = UUID.randomUUID();
        VoucherRepository voucherRepository = applicationContext.getBean(VoucherRepository.class);
        Voucher voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));

        OrderService orderService = applicationContext.getBean(OrderService.class);
        Order order = orderService.createOrder(customerId, orderItems, voucher.getVoucherId());
        Assert.isTrue(order.totalAmount() == 90L, MessageFormat.format("totalAmount {0} is not 90L", order.totalAmount()));

    }
}
