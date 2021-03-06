package com.turkcell.rentACar.business.request.additional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAdditionalRequest {

    private int additionalId;
    @NotNull
    private String name;
    @NotNull
    @Min(1)
    private double dailyPrice;
}
