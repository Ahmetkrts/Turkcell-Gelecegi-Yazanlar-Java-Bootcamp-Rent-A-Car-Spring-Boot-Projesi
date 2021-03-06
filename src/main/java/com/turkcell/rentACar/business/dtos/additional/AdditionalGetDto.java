package com.turkcell.rentACar.business.dtos.additional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalGetDto {

    private int additionalId;
    private String name;
    private double dailyPrice;
}
