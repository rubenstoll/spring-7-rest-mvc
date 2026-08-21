package guru.springframework.spring7restmvc.service;

import java.util.List;

import guru.springframework.spring7restmvc.model.Customer;

public interface CustomerService {

    Customer getCustomerById(Integer id);
    List<Customer> listCustomers();

    void createCustomer(Customer customer);
}
