package guru.springframework.spring7restmvc.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import guru.springframework.spring7restmvc.model.Customer;
import guru.springframework.spring7restmvc.services.CustomerService;
import guru.springframework.spring7restmvc.services.CustomerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@Slf4j
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    private CustomerServiceImpl customerServiceImpl = new CustomerServiceImpl();

    @BeforeEach
    void setUp() {
    }

    @Test
    void getCustomerList() throws Exception {

//        Customer customer = customerServiceImpl.listCustomers().getFirst();

        given(customerService.listCustomers()).willReturn(customerServiceImpl.listCustomers());

        mockMvc.perform(get("/api/v1/customer")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(3));

    }

    @Test
    void getCustomerById() throws Exception {

        Customer customer = customerServiceImpl.listCustomers().getFirst();
        when(customerService.getCustomerById(customer.getId())).thenReturn(customer);

        mockMvc.perform(get("/api/v1/customer/" + customer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(5)))
                .andExpect(jsonPath("$.customerName", is(customer.getCustomerName())));


    }

    @Test
    void createCustomer() {
        Customer customer = Customer.builder()
                .customerName("Matt Secret")
                .build();

//        ResponseEntity responseEntity = customerController.createCustomer(customer);
//
//        assertThat(responseEntity).isNotNull();
//        assertThat(responseEntity.getHeaders().size()).isGreaterThan(0);
//        assertThat(responseEntity.getHeaders().containsHeader(HttpHeaders.LOCATION)).isTrue();

    }
}