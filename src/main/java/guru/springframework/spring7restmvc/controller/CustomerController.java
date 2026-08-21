package guru.springframework.spring7restmvc.controller;

import java.util.List;

import guru.springframework.spring7restmvc.model.Customer;
import guru.springframework.spring7restmvc.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ruben
 * <p>
 * Spring 4.0 introduced the @RestController annotation in order to simplify the creation of RESTful web services.
 * It’s a convenient annotation that combines @Controller and @ResponseBody, which eliminates the need to annotate
 * every request handling method of the controller class with the @ResponseBody annotation.
 **/
@RestController
@RequestMapping("/api/v1/customer")
@Slf4j
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> getCustomerList() {
        return customerService.listCustomers();

    }

//    @RequestMapping("{customerId}")
//    @RequestMapping(value = "{customerId}", method =  RequestMethod.GET)
    @RequestMapping(value = "{customerId}")
    public Customer getCustomerById(@PathVariable("customerId") Integer customerId) {
        return customerService.getCustomerById(customerId);

    }

    @RequestMapping(method = RequestMethod.POST)
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        customerService.createCustomer(customer);
        log.debug("created new customer with ID {}", customer.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, "/api/v1/customer/" + customer.getId());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);

    }

}
