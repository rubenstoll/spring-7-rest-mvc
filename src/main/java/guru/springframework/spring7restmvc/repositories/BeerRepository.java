package guru.springframework.spring7restmvc.repositories;

import java.util.UUID;

import guru.springframework.spring7restmvc.entities.Beer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ruben
 **/
public interface BeerRepository extends JpaRepository<Beer, UUID> {

    @Transactional
    default Beer updateOrInsert(Beer entity) {
        return save(entity);
    }

}
