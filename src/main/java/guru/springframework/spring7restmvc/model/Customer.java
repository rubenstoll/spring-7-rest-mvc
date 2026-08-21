package guru.springframework.spring7restmvc.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by ruben
 **/
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private String customerName;
    //    private UUID customerId;
    private Integer id;
    private Long version;
    private LocalDate createdDate;
    private LocalDate lastModifiedDate;

}
