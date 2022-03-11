package com.turkcell.rentACar.business.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderedAdditionalRequest {

    private int orderedAdditionalId;
    @NotNull
    private int rentCarId;
    @NotNull
    private int additionalId;


}
