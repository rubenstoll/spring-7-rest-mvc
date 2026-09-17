package guru.springframework.spring7restmvc.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import guru.springframework.spring7restmvc.entities.Beer;
import guru.springframework.spring7restmvc.mappers.BeerMapper;
import guru.springframework.spring7restmvc.mappers.BeerMapperImpl;
import guru.springframework.spring7restmvc.model.BeerDTO;
import guru.springframework.spring7restmvc.services.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testSaveBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder()
                .beerName("BeerRubs")
                .build());
        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }

    @Test
    void getCustomersCheckId() {
        BeerServiceImpl beerServiceImpl = new BeerServiceImpl();
        BeerMapper beerMapper = new BeerMapperImpl();
        List<BeerDTO> customers = beerServiceImpl.listBeers();
        for (BeerDTO beerDTO : customers) {
            beerDTO.setId(null);
            beerRepository.save(beerMapper.beerDtoToBeer(beerDTO));
        }
        assertThat(beerRepository.count()).isEqualTo(3);
        assertThat(beerRepository.findAll().getFirst().getId()).isNotNull();

    }


}