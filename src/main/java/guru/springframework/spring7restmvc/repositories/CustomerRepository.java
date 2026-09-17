package guru.springframework.spring7restmvc.repositories;

import java.util.UUID;

import guru.springframework.spring7restmvc.entities.Customer;
import guru.springframework.spring7restmvc.model.CustomerDTO;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ruben
 **/
//@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    CustomerDTO getCustomerById(UUID id);
}
