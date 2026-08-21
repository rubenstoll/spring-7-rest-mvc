package guru.springframework.spring7restmvc.controller;

import java.util.List;
import java.util.UUID;

import guru.springframework.spring7restmvc.model.Beer;
import guru.springframework.spring7restmvc.service.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {

    private final BeerService beerService;


    @PutMapping("{beerId}")
//    public ResponseEntity<Beer> updateBeer(ObjectNode objectNode) {
//        UUID id = UUID.fromString(objectNode.get("id").asText());
//        Beer beer = objectNode.get("beer").asObject();
    public ResponseEntity<Beer> updateBeer(@PathVariable("beerId") UUID uuid, @RequestBody Beer beer) {

        beerService.updateBeer(uuid, beer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //    @RequestMapping(method = RequestMethod.POST)
    @PostMapping
    public ResponseEntity<Beer> handlePost(@RequestBody Beer beer) {
//    public ResponseEntity handlePost(Beer beer) {
        Beer savedBeer = beerService.saveNewBeer(beer);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, "/api/v1/beer/" + savedBeer.getId());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);

    }


    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> listBeers() {
        return beerService.listBeers();

    }

    @RequestMapping(value = "{beerId}", method = RequestMethod.GET)
    public Beer getBeerById(@PathVariable("beerId") UUID beerId) {
        log.debug("method getBeerById({}) in controller called 124", beerId);
        return beerService.getBeerById(beerId);

    }
}
