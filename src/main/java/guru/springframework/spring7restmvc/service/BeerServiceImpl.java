package guru.springframework.spring7restmvc.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import guru.springframework.spring7restmvc.model.Beer;
import guru.springframework.spring7restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BeerServiceImpl implements BeerService {

    private final Map<UUID, Beer> beerMap;

    public BeerServiceImpl() {
        this.beerMap = new HashMap<>();

        Beer beer1 = Beer.builder()
                .id(UUID.fromString("bbcc4621-d88f-4a94-ae2f-b38072bf5087"))
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.ALE)
                .upc("123456")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer2 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Crank")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("123456222")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer3 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Sunshine City")
                .beerStyle(BeerStyle.IPA)
                .upc("1234567777")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(144)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        beerMap.put(beer1.getId(), beer1);
        beerMap.put(beer2.getId(), beer2);
        beerMap.put(beer3.getId(), beer3);

    }

    /**
     *
     * @return list of beers
     */
    @Override
    public List<Beer> listBeers() {
        return new ArrayList<>(beerMap.values());

    }

    /**
     *
     */
    @Override
    public Beer getBeerById(UUID id) {
        log.debug("Get beer by Id - in service. Id: {} ", id.toString());
        return beerMap.get(id);

    }

    @Override
    public Beer saveNewBeer(Beer beer) {

        Beer beer1 = Beer.builder()
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .beerName(beer.getBeerName())
                .beerStyle(beer.getBeerStyle())
                .quantityOnHand(beer.getQuantityOnHand())
                .upc(beer.getUpc())
                .version(1)
                .price(beer.getPrice())
                .build();

        beerMap.put(beer1.getId(), beer1);
        return beer1;
    }

    @Override
    public void updateBeer(UUID uuid, Beer beer) {

        Beer existingBeer = getBeerById(uuid);
        existingBeer.setBeerName(beer.getBeerName());
        existingBeer.setPrice(beer.getPrice());
        existingBeer.setUpc(beer.getUpc());
        existingBeer.setQuantityOnHand(beer.getQuantityOnHand());
//        existingBeer.setBeerStyle(beer.getBeerStyle());

        beerMap.put(existingBeer.getId(), existingBeer);
    }
}
