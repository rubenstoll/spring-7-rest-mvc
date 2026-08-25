package guru.springframework.spring7restmvc.controller;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import guru.springframework.spring7restmvc.model.Customer;
import guru.springframework.spring7restmvc.services.CustomerService;
import guru.springframework.spring7restmvc.services.CustomerServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@WebMvcTest(CustomerController.class)
@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    private CustomerServiceImpl customerServiceImpl;

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private InternalResourceViewResolver internalResourceViewResolver;

    @BeforeEach
    void setUp() {
        this.customerServiceImpl = new CustomerServiceImpl();
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
    void getCustomerByIdNotFound() throws Exception {
        given(customerService.getCustomerById(anyInt())).willReturn(Optional.empty());

        log.debug("CustomerController getCustomerByIdNotFound TEST");
        mockMvc.perform(get(CustomerController.CUSTOMER_API + "/" + 988888))
                .andExpect(status().isNotFound());


    }

    @Test
    void getCustomerById() throws Exception {
        Customer customer = customerServiceImpl.listCustomers().getFirst();
        when(customerService.getCustomerById(customer.getId())).thenReturn(Optional.of(customer));

        log.debug("Customer ID {}", customer.getId());

        mockMvc.perform(get(CustomerController.CUSTOMER_API + "/" + customer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(5)))
                .andExpect(jsonPath("$.customerName", is(customer.getCustomerName())));


    }


    @Test
    void convertObject2Json() {
//        ObjectMapper objectMapper = new ObjectMapper();
        Customer customer = customerServiceImpl.listCustomers().getFirst();

//        ObjectNode customerJson = objectMapper.createObjectNode();
//        customerJson.put("id", customer.getId());

        log.debug(this.objectMapper.writeValueAsString(customer));

    }

    @Test
    void createNewCustomer() throws Exception {
        Customer customer = customerServiceImpl.listCustomers().getFirst();

        given(customerService.listCustomers()).willReturn(customerServiceImpl.listCustomers());
        when(customerService.createCustomer(any(Customer.class))).thenReturn(customer);

        mockMvc.perform(post("/api/v1/customer").accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));

    }

    @Test
    void updateCustomer() throws Exception {
        Customer customer = customerServiceImpl.listCustomers().getFirst();

//        doNothing().when(customerService.updateCustomer(customer.getId(), customer));

        mockMvc.perform(put("/api/v1/customer/path/" + customer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isNoContent());

        verify(customerService).updateCustomer(anyInt(), any(Customer.class));

    }

    @Captor
    ArgumentCaptor<Integer> integerArgumentCaptor;

    @Test
    void deleteCustomer() throws Exception {

        Customer customer = customerServiceImpl.listCustomers().getFirst();

        mockMvc.perform(delete("/api/v1/customer/" + customer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

//        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
//        verify(customerService).deleteCustomer(captor.capture());
//        assertThat(customer.getId()).isEqualTo(captor.getValue());

// use with @Captor annotation, but for some reason does not work
        verify(customerService).deleteCustomer(integerArgumentCaptor.capture());
        assertThat(customer.getId()).isEqualTo(integerArgumentCaptor.getValue());

    }

    @Captor
    ArgumentCaptor<Customer> customerArgumentCaptor;

    @Test
    void patchCustomer() throws Exception {

        Customer customer = customerServiceImpl.listCustomers().getFirst();

        Map<String, Object> customerMap = new HashMap<>();
        customerMap.put("customerName", "New Name");

        mockMvc.perform(patch("/api/v1/customer/" + customer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerMap)))
                .andExpect(status().isNoContent());

        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);
        ArgumentCaptor<Integer> integerArgumentCaptor = ArgumentCaptor.forClass(Integer.class);

        verify(customerService).patchCustomer(integerArgumentCaptor.capture(), captor.capture());

        assertThat(integerArgumentCaptor.getValue()).isEqualTo(customer.getId());
        assertThat(captor.getValue().getCustomerName()).isEqualTo(customer.getCustomerName());

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