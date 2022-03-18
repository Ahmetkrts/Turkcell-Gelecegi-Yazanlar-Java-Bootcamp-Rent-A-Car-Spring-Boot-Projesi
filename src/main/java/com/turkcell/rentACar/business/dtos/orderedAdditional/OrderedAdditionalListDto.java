package com.turkcell.rentACar.business.dtos.orderedAdditional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedAdditionalListDto {

    private int orderedAdditionalId;
    private int rentCar_Customer_CustomerId;
    private String additionalName;


}
