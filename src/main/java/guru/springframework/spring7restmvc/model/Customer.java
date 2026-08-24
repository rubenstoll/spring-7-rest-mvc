package guru.springframework.spring7restmvc.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tools.jackson.databind.annotation.JsonDeserialize;

/**
 * Created by ruben
 **/
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(builder = Customer.CustomerBuilder.class)
public class Customer {

    @JsonProperty("customerName")
    private String customerName;
    //    private UUID customerId;
    private Integer id;
    private Long version;
    private LocalDate createdDate;
    private LocalDate lastModifiedDate;

}
