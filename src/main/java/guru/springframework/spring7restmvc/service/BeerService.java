package guru.springframework.spring7restmvc.service;

import java.util.List;
import java.util.UUID;

import guru.springframework.spring7restmvc.model.Beer;

public interface BeerService {

    List<Beer> listBeers();

    Beer getBeerById(UUID id);

    Beer saveNewBeer(Beer beer);

    void updateBeer(UUID uuid, Beer beer);
}
