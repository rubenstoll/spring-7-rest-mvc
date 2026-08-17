package guru.springframework.spring7restmvc.controller;

import java.util.UUID;

import guru.springframework.spring7restmvc.BeerService;
import guru.springframework.spring7restmvc.model.Beer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@AllArgsConstructor
@Controller
@Slf4j
public class BeerController {

    private final BeerService beerService;

    public Beer getBeerById(UUID id) {
        log.debug("method getBeerById({}) in controller called ", id);
        return beerService.getBeerById(id);

    }
}
