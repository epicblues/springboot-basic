package org.prgrms.kdt.domain;

import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@ToString
public class FixedAmountVoucher implements Voucher{

    private final UUID voucherId;
    private final long discount;

    public FixedAmountVoucher(UUID voucherId, long discount) {
        this.voucherId = voucherId;
        this.discount = discount;
    }

    @Override
    public String getType() {
        return "FIXED";
    }

    @Override
    public long discountCoupon() {
        return discount;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - discount;
    }


}
