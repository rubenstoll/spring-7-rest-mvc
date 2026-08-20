package guru.springframework.spring7restmvc.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 *
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Beer {

    private UUID id;
    private Integer version;
    private String beerName;
    private BeerStyle beerStyle;
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;


//    @JsonCreator
//    public Beer(@JsonProperty("beerName") String beerName, @JsonProperty("beerStyle") BeerStyle beerStyle, @JsonProperty("upc") String upc,
//                @JsonProperty("quantityOnHand") Integer quantityOnHand, @JsonProperty("price") BigDecimal price) {
//        this.beerName = beerName;
//        this.beerStyle = beerStyle;
//        this.upc = upc;
//        this.quantityOnHand = quantityOnHand;
//        this.price = price;
//        this.createdDate = LocalDateTime.now();
//        this.updateDate = LocalDateTime.now();
//
//    }


}
