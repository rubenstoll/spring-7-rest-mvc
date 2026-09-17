package guru.springframework.spring7restmvc.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import guru.springframework.spring7restmvc.entities.Customer;
import guru.springframework.spring7restmvc.mappers.CustomerMapper;
import guru.springframework.spring7restmvc.model.CustomerDTO;
import guru.springframework.spring7restmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * Created by ruben
 **/
@Primary
@Service
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID uuid) {
        return Optional.ofNullable((customerMapper.customerToCustomerDTO(customerRepository.findById(uuid)
                .orElse(null))));
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
//        return Collections.emptyList();
        return customerRepository.findAll().stream()
                .map(customerMapper::customerToCustomerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customerDTO) {
        Customer customer = customerRepository.save(customerMapper.customerDtoToCustomer(customerDTO));
        return customerMapper.customerToCustomerDTO(customer);

    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customerDTO) {

//        if (customerRepository.existsById(customerId)) {
//            Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
//            customer.setName(customerDTO.getName());
//            customer.setVersion(customer.getVersion() + 1);
//            customerRepository.save(customer);
//            return Optional.of(customerMapper.customerToCustomerDTO(customer));
//        }
//        return Optional.empty();

        AtomicReference<Optional<CustomerDTO>> optionalAtomicReference = new AtomicReference<>();

        customerRepository.findById(customerId).ifPresentOrElse(foundCustomer -> {
            foundCustomer.setName(customerDTO.getName());
            optionalAtomicReference.set(Optional.of(customerMapper.customerToCustomerDTO(customerRepository.save(foundCustomer))));
        }, () -> optionalAtomicReference.set(Optional.empty()));
        return optionalAtomicReference.get();

    }

    @Override
    public Boolean deleteCustomerById(UUID customerId) {
        if (customerRepository.existsById(customerId)) {
            customerRepository.deleteById(customerId);
            return true;
        }
        return false;
    }

    @Override
    public void patchCustomerById(UUID customerId, CustomerDTO customerDTO) {

        if (customerRepository.existsById(customerId)) {
            var customer = customerRepository.findById(customerId).get();
            if ((customerDTO.getName() != null)) {
                customer.setName(customerDTO.getName());
            }
            customerRepository.save(customer);
        }

    }
}
