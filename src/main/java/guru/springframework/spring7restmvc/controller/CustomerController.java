package guru.springframework.spring7restmvc.controller;

import java.util.List;

import guru.springframework.spring7restmvc.model.Customer;
import guru.springframework.spring7restmvc.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    private final static String CUSTOMER_API_URL = "/api/v1/customer";

    @PatchMapping("/{customerId}")
    public ResponseEntity patchCustomer(@PathVariable("customerId") Integer customerId, @RequestBody Customer customer) {
        customerService.patchCustomer(customerId, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @DeleteMapping("/{customerId}")
//    @RequestMapping(value = "/{customerId}", method = {RequestMethod.DELETE, RequestMethod.GET})
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("customerId") Integer customerId) {

        customerService.deleteCustomer(customerId);
        log.debug("Deleted customer with id {}", customerId);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, CUSTOMER_API_URL + customerId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    //**********************************************************************
    // https://www.baeldung.com/spring-requestparam-vs-pathvariable
    //**********************************************************************
    @PutMapping("/path/{customerId}")
    public ResponseEntity<Customer> updateCustomerPath(@PathVariable("customerId") Integer customerId, @RequestBody Customer customer) {
        HttpHeaders headers = updateCustomerGetHeaders(customerId, customer);
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);

    }

    @PutMapping("/param")
    public ResponseEntity<Customer> updateCustomerParam(@RequestParam("customerId") Integer customerId, @RequestBody Customer customer) {
        HttpHeaders headers = updateCustomerGetHeaders(customerId, customer);
        return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);

    }


    private @NonNull HttpHeaders updateCustomerGetHeaders(Integer customerId, Customer customer) {
        customerService.updateCustomer(customerId, customer);
        log.debug("Updated customer with id {}", customerId);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, CUSTOMER_API_URL + "/" + customerId);
        return headers;

    }


    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> getCustomerList() {
        return customerService.listCustomers();

    }

    //    @RequestMapping("{customerId}")
//    @RequestMapping(value = "{customerId}", method =  RequestMethod.GET)
//    @RequestMapping(value = "{customerId}")
    @GetMapping("/{customerId}")
    public Customer getCustomerById(@PathVariable("customerId") Integer customerId) {
        return customerService.getCustomerById(customerId);

    }

    //    @RequestMapping(method = RequestMethod.POST)
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        customerService.createCustomer(customer);
        log.debug("created new customer with ID {}", customer.getId());
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, "/api/v1/customer/" + customer.getId());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);

    }

}
