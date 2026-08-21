package guru.springframework.spring7restmvc.controller;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BeerControllerTest {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(BeerControllerTest.class);
    @Autowired
    private BeerController controller;
    // controller creates new beer service which creates three beers to use a test data - should be done in a database.
    private UUID beerSvcImplBeer1HardCodedId;

    @BeforeEach
    void setUp() {
        this.beerSvcImplBeer1HardCodedId = UUID.fromString("bbcc4621-d88f-4a94-ae2f-b38072bf5087");

    }


    @Test
    void getBeerById() {
        log.debug("call controller");
        var x = controller.getBeerById(beerSvcImplBeer1HardCodedId);
        assertThat(x).isNotNull();
        log.debug("getBeerById() returned UUID {}", x.getId());
        assertThat(x.getId()).isEqualTo(x.getId());
    }
}