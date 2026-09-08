package guru.springframework.spring7restmvc.repositories;

import java.util.UUID;

import guru.springframework.spring7restmvc.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ruben
 **/
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
