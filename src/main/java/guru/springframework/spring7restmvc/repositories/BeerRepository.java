package guru.springframework.spring7restmvc.repositories;

import java.util.UUID;

import guru.springframework.spring7restmvc.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ruben
 **/
public interface BeerRepository extends JpaRepository<Beer, UUID> {
}
