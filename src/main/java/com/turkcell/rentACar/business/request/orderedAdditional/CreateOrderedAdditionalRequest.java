package com.turkcell.rentACar.business.request.orderedAdditional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderedAdditionalRequest {

    @NotNull
    private int rentCarId;
    @NotNull
    private int additionalId;


}
