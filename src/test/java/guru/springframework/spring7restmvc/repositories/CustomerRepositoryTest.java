package guru.springframework.spring7restmvc.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import guru.springframework.spring7restmvc.entities.Customer;
import guru.springframework.spring7restmvc.mappers.CustomerMapper;
import guru.springframework.spring7restmvc.mappers.CustomerMapperImpl;
import guru.springframework.spring7restmvc.model.CustomerDTO;
import guru.springframework.spring7restmvc.services.CustomerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

@Slf4j
@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;
//    @Autowired
//    private CustomerMapper customerMapper;

    @Test
    void testSaveCustomer() {
        Customer savedCustomer = customerRepository.save(Customer.builder()
                .name("Ruben")
                .build());
        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isNotNull();

    }


    @Test
    void getCustomersCheckId() {
        CustomerServiceImpl customerService = new CustomerServiceImpl();
        CustomerMapper customerMapper = new CustomerMapperImpl();
        List<CustomerDTO> customers = customerService.getAllCustomers();
        for (CustomerDTO customerDTO : customers) {
            customerDTO.setId(null);
            customerRepository.save(customerMapper.customerDtoToCustomer(customerDTO));
        }

        assertThat(customerRepository.count()).isEqualTo(3);

        List<Customer> foundCustomers = customerRepository.findAll().stream()
                .filter(customer -> customer.getId() != null)
                .toList();

        assertThat(foundCustomers.size()).isEqualTo(3);

        assertThat(customerRepository.findAll().getFirst()).satisfiesAnyOf(customer -> {
            assertThat(customer.getId()).isNotNull();
            assertThat(customer.getName()).contains("Customer");
        });

        List<Customer> customerList = customerRepository.findAll();
        customerList.forEach(customer -> log.debug("customer {} and id {}", customer.getName(), customer.getId()));
        assertThat(customerRepository.findAll().getFirst().getId()).isNotNull();
        assertThat(foundCustomers.size()).isEqualTo(3);

        assertThat(foundCustomers.getLast().getName())
                .isNotNull()
                .startsWith("Cust")
//                .endsWith("1")
                .containsPattern(".*\\d");

        assertThat(foundCustomers).hasSize(3)
                .allMatch(customer -> customer.getId().equals(customer.getId()));
    }


}