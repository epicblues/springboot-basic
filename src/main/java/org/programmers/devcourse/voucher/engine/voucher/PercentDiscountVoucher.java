package org.programmers.devcourse.voucher.engine.voucher;

import java.text.MessageFormat;
import java.util.UUID;
import org.programmers.devcourse.voucher.engine.exception.VoucherDataOutOfRangeException;

public class PercentDiscountVoucher implements
    Voucher {

  private final UUID voucherId;
  private final long discountPercent;

  private PercentDiscountVoucher(UUID voucherId, long discountPercent) {
    this.voucherId = voucherId;
    this.discountPercent = discountPercent;
  }

  public static PercentDiscountVoucher from(long discountPercent)
      throws VoucherDataOutOfRangeException {
    if (discountPercent > 100 || discountPercent < 0) {
      throw new VoucherDataOutOfRangeException("Discount percent out of range(0-100)");
    }

    return new PercentDiscountVoucher(UUID.randomUUID(), discountPercent);
  }


  @Override
  public UUID getVoucherId() {
    return voucherId;
  }


  @Override
  public long discount(long beforeDiscount) {
    return beforeDiscount * (100 - discountPercent) / 100;
  }

  @Override
  public String toString() {
    return MessageFormat.format("PercentDiscountVoucher : Id = {0}, DiscountPercent = {1}%",
        voucherId,
        discountPercent);
  }
}