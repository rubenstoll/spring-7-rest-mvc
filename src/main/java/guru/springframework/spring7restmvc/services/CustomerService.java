package guru.springframework.spring7restmvc.services;

import java.util.List;
import java.util.Optional;

import guru.springframework.spring7restmvc.model.Customer;

public interface CustomerService {

    Optional<Customer> getCustomerById(Integer id);

    List<Customer> listCustomers();

    Customer createCustomer(Customer customer);

    Customer updateCustomer(Integer customerId, Customer customer);

    void deleteCustomer(Integer id);

    void patchCustomer(Integer customerId, Customer customer);
}
