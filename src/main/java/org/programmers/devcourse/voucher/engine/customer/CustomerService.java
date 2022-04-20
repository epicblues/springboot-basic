package org.programmers.devcourse.voucher.engine.customer;

import java.util.List;

public class CustomerService {

  private final CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public List<Customer> getAllCustomers() {
    return customerRepository.getAll();
  }
}
