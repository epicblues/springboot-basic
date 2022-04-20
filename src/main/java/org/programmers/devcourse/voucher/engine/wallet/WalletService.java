package org.programmers.devcourse.voucher.engine.wallet;

import java.util.List;
import org.programmers.devcourse.voucher.engine.customer.Customer;
import org.programmers.devcourse.voucher.engine.customer.CustomerRepository;
import org.programmers.devcourse.voucher.engine.voucher.entity.Voucher;
import org.programmers.devcourse.voucher.engine.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

  private final VoucherRepository voucherRepository;
  private final CustomerRepository customerRepository;

  public WalletService(VoucherRepository voucherRepository, CustomerRepository customerRepository) {
    this.voucherRepository = voucherRepository;
    this.customerRepository = customerRepository;
  }

  public List<Voucher> getUnassignedVouchers() {
    return voucherRepository.getUnassignedVouchers();
  }


  public void assignVoucher(Voucher voucherToAssign, Customer customerToAssign) {
    voucherRepository.update(voucherToAssign, customerToAssign);
  }
}
