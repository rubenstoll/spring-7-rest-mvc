package guru.springframework.spring7restmvc.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.UUID;

import guru.springframework.spring7restmvc.entities.Customer;
import guru.springframework.spring7restmvc.mappers.CustomerMapper;
import guru.springframework.spring7restmvc.model.CustomerDTO;
import guru.springframework.spring7restmvc.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
class CustomerControllerIT {

    @Autowired
    private CustomerController customerController;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
    }

    @Rollback
    @Transactional
    @Test
    void emptyList() {
        customerRepository.deleteAll();
        List<CustomerDTO> customerDTOS = customerController.listAllCustomers();
        Assertions.assertThat(customerDTOS.size()).isEqualTo(0);

    }


    @Test
    void listAllCustomers() {
        List<CustomerDTO> customerDTOs = customerController.listAllCustomers();
        assertThat(customerDTOs.size()).isEqualTo(3);

    }

    @Test
    void getCustomerById() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerController.getCustomerById(customer.getId());
        assertThat(customerDTO.getId()).isEqualTo(customer.getId());

    }

    @Test
    void notFound() {
        assertThrows(NotFoundException.class, () -> customerController.getCustomerById(UUID.randomUUID()));
    }


    @Rollback
    @Transactional
    @Test
    void saveCustomerTest() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .name("Customer")
                .build();
        ResponseEntity<Customer> customerResponseEntity = customerController.handlePost(customerDTO);

        Assertions.assertThat(customerResponseEntity).isNotNull();

        assertThat(customerResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        assertThat(customerResponseEntity.getHeaders().get("Location")).isNotNull();

        String[] locationUUId = customerResponseEntity.getHeaders().getLocation().getPath().split("/");
        UUID uuid = UUID.fromString(locationUUId[4]);

        Customer customer = customerRepository.findById(uuid).get();
        assertThat(customer).isNotNull();

    }


    @Test
    void testUpdateNotFound() {
        assertThrows(NotFoundException.class, () -> customerController.getCustomerById(UUID.randomUUID()));

    }

    @Transactional
    @Rollback
    @Test
    void updateExistingCustomer() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
        log.info("customerDTO: {}", customerDTO);
        log.info("customer name and ID BEFORE update {}, {}", customerDTO.getName(), customerDTO.getId());
        customerDTO.setId(customer.getId());
        customerDTO.setVersion(customer.getVersion());
        final String customerName = "Pepito New Name";
        customerDTO.setName(customerName);

        ResponseEntity responseEntity = customerController.updateCustomerByID(customer.getId(), customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Customer updatedCustomer = customerRepository.findById(customer.getId()).get();
        log.info("customer name and ID AFTER update {}, {}", updatedCustomer.getName(), updatedCustomer.getId());
        assertThat(updatedCustomer.getName()).isEqualTo(customerName);

    }

    @Test
    void patchCustomerById() {
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customerRepository.findAll().getFirst());
        CustomerDTO customerPatched = CustomerDTO.builder()
                .id(customerDTO.getId())
                .name("Pepe")
                .build();
        customerController.patchCustomerById(customerDTO.getId(), customerPatched);
        CustomerDTO customerById = customerRepository.getCustomerById(customerDTO.getId());
        assertThat(customerById.getName()).isEqualTo(customerPatched.getName());

    }

    @Rollback
    @Transactional
    @Test
    void deleteByIdFound() {
        Customer customer = customerRepository.findAll().get(0);
        ResponseEntity responseEntity = customerController.deleteCustomerById(customer.getId());
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Assertions.assertThat(customerRepository.findById(customer.getId())).isEmpty();

    }

    @Transactional
    @Rollback
    @Test
    void deleteByIdNotFound() {
        assertThrows(NotFoundException.class, () -> {
            customerController.deleteCustomerById(UUID.randomUUID());
        });
    }


}