package org.prgrms.kdtspringhomework;

import org.prgrms.kdtspringhomework.config.AppConfiguration;
import org.prgrms.kdtspringhomework.customer.service.CustomerService;
import org.prgrms.kdtspringhomework.io.OutputConsole;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BlackCustomerApplication {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);
        CustomerService customerService = applicationContext.getBean(CustomerService.class);
        OutputConsole outputConsole = new OutputConsole();

        executeBlacklistProgram(customerService, outputConsole);
    }

    private static void executeBlacklistProgram(CustomerService customerService, OutputConsole outputConsole) {
        customerService.listBlackCustomers();
        outputConsole.startBlackList();
        customerService.printCustomers();
    }
}