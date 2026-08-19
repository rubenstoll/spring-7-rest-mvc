package guru.springframework.spring7restmvc.model;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

/**
 * Created by ruben
 **/
@Data
@Builder
public class Customer {

    private String customerName;
//    private UUID customerId;
    private Integer id;
    private Long version;
    private LocalDate createdDate;
    private LocalDate lastModifiedDate;

}
