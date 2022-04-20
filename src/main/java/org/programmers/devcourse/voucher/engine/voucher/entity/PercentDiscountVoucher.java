package org.programmers.devcourse.voucher.engine.voucher.entity;

import java.text.MessageFormat;
import java.util.UUID;
import org.programmers.devcourse.voucher.engine.exception.VoucherDiscountDegreeOutOfRangeException;
import org.programmers.devcourse.voucher.engine.exception.VoucherException;
import org.programmers.devcourse.voucher.engine.voucher.VoucherFactory;

public class PercentDiscountVoucher extends
    AbstractVoucher {

  public static final VoucherFactory factory = new VoucherFactory() {
    @Override
    public Voucher create(UUID id, long discountDegree, UUID ownerId) throws VoucherException {
      return new PercentDiscountVoucher(id, discountDegree, ownerId);
    }

    @Override
    public Voucher create(UUID id, long discountDegree) {
      return new PercentDiscountVoucher(id, discountDegree, null);
    }
  };
  private final UUID voucherId;
  private final long discountPercent;

  private PercentDiscountVoucher(UUID voucherId, long discountPercent, UUID ownerId)
      throws VoucherDiscountDegreeOutOfRangeException {
    if (discountPercent > 100 || discountPercent <= 0) {
      throw new VoucherDiscountDegreeOutOfRangeException("Discount percent out of range(1-100)");
    }
    this.voucherId = voucherId;
    this.discountPercent = discountPercent;
    this.ownerId = ownerId;
  }


  @Override
  public long getDiscountDegree() {
    return discountPercent;
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
