package org.programmers.devcourse.voucher.engine.voucher.entity;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

public abstract class AbstractVoucher implements Voucher {

  @Getter
  @Setter
  protected UUID ownerId = null;

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj instanceof Voucher) {
      return this.getVoucherId().equals(((Voucher) obj).getVoucherId());

    }

    return false;
  }

  @Override
  public int hashCode() {
    return this.getVoucherId().hashCode();
  }

}
