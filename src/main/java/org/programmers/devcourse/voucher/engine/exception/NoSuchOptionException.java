package org.programmers.devcourse.voucher.engine.exception;

public class NoSuchOptionException extends VoucherException {

  public NoSuchOptionException() {
    super("No option available!");
  }
}