package org.programmers.devcourse.voucher.engine.wallet;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import org.programmers.devcourse.voucher.engine.customer.Customer;
import org.programmers.devcourse.voucher.engine.customer.CustomerService;
import org.programmers.devcourse.voucher.engine.io.Input;
import org.programmers.devcourse.voucher.engine.io.Output;
import org.programmers.devcourse.voucher.engine.voucher.entity.Voucher;
import org.springframework.stereotype.Component;

@Component
public class WalletController {

  private final Input input;
  private final Output output;
  private final WalletService walletService;

  private final CustomerService customerService;

  public WalletController(Input input, Output output, WalletService walletService) {
    this.input = input;
    this.output = output;
    this.walletService = walletService;
  }


  public void initialize() throws IOException {
    try {
      String menu = input.getString("==Voucher Wallet==", "Enter Menu", "0. exit", "1. assign",
          "2. Check Customer's Voucher", "3. Remove Voucher", "4. Check Voucher's owner");
      switch (WalletMenu.from(menu).orElseThrow(RuntimeException::new)) {
        case EXIT:
          output.print("Return to main menu");
          return;
        case ASSIGN:
          // 먼저 할당되지 않은 모든 바우처 목록를 가져와야 한다.
          List<Voucher> vouchers = walletService.getUnassignedVouchers();
          AtomicInteger voucherIndex = new AtomicInteger(0);
          String[] instructionBuffer = vouchers.stream()
              .map(voucher -> voucherIndex.incrementAndGet() + ". " + voucher.toString())
              .toArray(size -> new String[size + 1]);
          instructionBuffer[instructionBuffer.length - 1] = "Enter number of voucher to assign";
          long voucherSelection = input.getLong(instructionBuffer) - 1;

          List<Customer> customers = customerService.getAllCustomers();
          AtomicInteger customerIndex = new AtomicInteger(0);
          String[] customersBuffer = customers.stream()
              .map(customer -> customerIndex.incrementAndGet() + ". " + customer.toString())
              .toArray(size -> new String[size + 1]);
          customersBuffer[customersBuffer.length - 1] = "Enter number of customers to assign";

          long customerSelection = input.getLong(customersBuffer) - 1;

          var voucherToAssign = vouchers.get((int) voucherSelection);
          var customerToAssign = customers.get((int) customerSelection);

          walletService.assignVoucher(voucherToAssign, customerToAssign);

          output.print(MessageFormat.format("Assign voucher : {0} --> customer : {1} success!",
              voucherToAssign.getVoucherId(), customerToAssign.getCustomerId()));

          break;
        default:
          break;
      }

    } catch (RuntimeException exception) {
      output.print("입력 오류");
    }

  }


  private enum WalletMenu {
    EXIT("0"), ASSIGN("1"), CHECK_VOUCHER("2"), REMOVE_VOUCHER("3"), CHECK_CUSTOMER("4");


    private final String menuId;

    WalletMenu(String menuId) {
      this.menuId = menuId;
    }

    static Optional<WalletMenu> from(String menuId) {
      for (WalletMenu menu : WalletMenu.values()) {
        if (menu.menuId.equals(menuId)) {
          return Optional.of(menu);
        }
      }

      return Optional.empty();

    }


  }
}
