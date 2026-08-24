package guru.springframework.spring7restmvc.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import guru.springframework.spring7restmvc.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by ruben
 **/
@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final Map<Integer, Customer> customers;

    public CustomerServiceImpl() {
        this.customers = new HashMap<>();
        fetchCustomers();
    }

    @Override
    public Customer getCustomerById(Integer id) {
        return customers.get(id);
    }

    @Override
    public List<Customer> listCustomers() {
        log.debug("getting customer in service .. ");
        return new ArrayList<>(customers.values());

    }

    @Override
    public Customer createCustomer(Customer customer) {

        int customerId = ThreadLocalRandom.current().nextInt(0, 1000);
        customer.setId(customerId);
        customer.setVersion(1L);
        customer.setCreatedDate(LocalDate.now());
        customer.setLastModifiedDate(LocalDate.now());
        customers.put(customer.getId(), customer);

        return customer;
    }


    // mock DB
    private void fetchCustomers() {

        Customer customer1 = Customer.builder()
                .id(1)
                .customerName("John Kelly")
                .version(1L)
                .createdDate(LocalDate.now())
                .lastModifiedDate(LocalDate.now())
                .build();
        customers.put(customer1.getId(), customer1);

        Customer customer2 = Customer.builder()
                .id(2)
                .customerName("Matt Secret")
                .version(1L)
                .createdDate(LocalDate.now())
                .lastModifiedDate(LocalDate.now())
                .build();
        customers.put(customer2.getId(), customer2);

        Customer customer3 = Customer.builder()
                .id(3)
                .customerName("Martha Robinson")
                .version(1L)
                .createdDate(LocalDate.now())
                .lastModifiedDate(LocalDate.now())
                .build();
        customers.put(customer3.getId(), customer3);


    }

    @Override
    public Customer updateCustomer(Integer customerId, Customer customer) {

        log.debug("updating customer with id {}", customerId);
        Customer updatedCustomer = customers.get(customerId);
        updatedCustomer.setCustomerName(customer.getCustomerName());
        updatedCustomer.setVersion(updatedCustomer.getVersion() + 1);
        updatedCustomer.setLastModifiedDate(LocalDate.now());

        customers.replace(customerId, updatedCustomer);
        return updatedCustomer;


    }

    @Override
    public void deleteCustomer(Integer id) {
        log.debug("deleting customer with id {}", id);
        customers.remove(id);

    }

    @Override
    public void patchCustomer(Integer customerId, Customer customer) {
        log.debug("patching customer with id {}", customerId);
        Customer existingCustomer = customers.get(customerId);

        if ((existingCustomer != null)) {
            if (StringUtils.hasText(customer.getCustomerName())) {
                existingCustomer.setCustomerName(customer.getCustomerName());
            }
        }
    }

}
