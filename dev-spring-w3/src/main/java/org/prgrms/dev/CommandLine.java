package org.prgrms.dev;

import org.prgrms.dev.command.CommandType;
import org.prgrms.dev.io.Console;
import org.prgrms.dev.io.Input;
import org.prgrms.dev.io.Output;
import org.prgrms.dev.voucher.domain.FixedAmountVoucher;
import org.prgrms.dev.voucher.domain.PercentDiscountVoucher;
import org.prgrms.dev.voucher.domain.Voucher;
import org.prgrms.dev.voucher.domain.VoucherType;
import org.prgrms.dev.voucher.service.VoucherService;
import org.springframework.context.ApplicationContext;

import java.util.Optional;
import java.util.UUID;

public class CommandLine implements Runnable {
    private Input input;
    private Output output;
    private ApplicationContext applicationContext;

    public CommandLine(Console input, Console output, ApplicationContext applicationContext) {
        this.input = input;
        this.output = output;
        this.applicationContext = applicationContext;
    }

    @Override
    public void run() {
        VoucherService voucherService = applicationContext.getBean(VoucherService.class);
        while (true) {
            output.init();
            String inputCommandType = input.input("> ");
            boolean flag = CommandType.execute(inputCommandType, input, output, voucherService);
            if(!flag) break;
        }
    }
}
