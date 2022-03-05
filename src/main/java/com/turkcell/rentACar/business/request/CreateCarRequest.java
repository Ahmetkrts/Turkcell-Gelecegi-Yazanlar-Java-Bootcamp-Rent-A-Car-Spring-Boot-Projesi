package com.turkcell.rentACar.business.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest {

    @Min(50)
    @NotNull
    private int dailyPrice;
    @NotNull
    @Min(2000)
    private int modelYear;
    @NotNull
    @Size(min = 2, max = 50)
    private String description;

    private int brandId;
    private int colorId;
}
