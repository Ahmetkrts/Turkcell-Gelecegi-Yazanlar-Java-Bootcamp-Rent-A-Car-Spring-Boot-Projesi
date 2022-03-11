package com.turkcell.rentACar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedAdditionalGetDto {

    private int orderedAdditionalId;
    private String rentCarRentFirstName;
    private String additionalName;


}
