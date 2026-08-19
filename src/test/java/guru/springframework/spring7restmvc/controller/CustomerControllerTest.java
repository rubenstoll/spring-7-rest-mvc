package guru.springframework.spring7restmvc.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import guru.springframework.spring7restmvc.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class CustomerControllerTest {

    @Autowired
    private CustomerController customerController;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getCustomerList() {
        List<Customer> customerList = customerController.getCustomerList();

        log.debug(customerList.toString());

        assertThat(customerList).isNotNull();
        assertThat(customerList.size()).isEqualTo(3);

        assertThat(customerList)
                .isNotEmpty()
                .anySatisfy(customer -> assertThat(customer.getCustomerName()).contains("Martha"));

    }

    @Test
    void getCustomerById() {
        Customer customer = customerController.getCustomerById(2);
        log.debug(customer.toString());
        assertThat(customer)
                .satisfies(c -> {
                    assertThat(c.getCustomerName()).isEqualTo("Matt Secret");
                    assertThat(customer.getVersion()).isEqualTo(1);
                });

    }
}