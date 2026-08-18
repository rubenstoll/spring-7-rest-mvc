package guru.springframework.spring7restmvc.controller;

import java.util.List;
import java.util.UUID;

import guru.springframework.spring7restmvc.service.BeerService;
import guru.springframework.spring7restmvc.model.Beer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {

    private final BeerService beerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> listBeers() {
        return beerService.listBeers();

    }

    @RequestMapping(value = "{beerId}", method =  RequestMethod.GET)
    public Beer getBeerById(@PathVariable("beerId") UUID beerId) {
        log.debug("method getBeerById({}) in controller called ", beerId);
        return beerService.getBeerById(beerId);

    }
}
