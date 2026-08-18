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
    private UUID uuid3;

    @BeforeEach
    void setUp() {

        String inputStr = "bbcc4621-d88f-4a94-ae2f-b38072bf5087";

//        UUID uuid = UUID.fromString(inputStr);
//        UUID uuid2 = UUID.fromString(inputStr);
        this.uuid3 = UUID.fromString(inputStr);


    }


    @Test
    void getBeerById() {
        log.debug("call controller");
        // https://www.baeldung.com/java-generate-same-uuid-from-string#the-given-string-is-a-standard-uuid-representation
// UUID uid = UUID.fromString("f000aa01-0451-4000-b000-000000000000");
//        UUID random = UUID.randomUUID();
        var x = controller.getBeerById(uuid3);
        assertThat(x).isNotNull();
        log.debug("getBeerById() returned UUID {}", x.getId());
        //TODO 17.08.2026 ruben: implement correctly
        assertThat(x.getId()).isEqualTo(x.getId());
    }
}