package guru.springframework.spring7restmvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import guru.springframework.spring7restmvc.model.Beer;
import guru.springframework.spring7restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BeerServiceImpl implements BeerService {


    @Override
    public Beer getBeerById(UUID id) {
        log.debug("method getBeerById({}) called ", id);

        return Beer.builder()
                .id(id)
                .version(2)
                .beerName("Beer Name")
                .beerStyle(BeerStyle.LAGER)
                .upc("12345")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(4)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }
}
