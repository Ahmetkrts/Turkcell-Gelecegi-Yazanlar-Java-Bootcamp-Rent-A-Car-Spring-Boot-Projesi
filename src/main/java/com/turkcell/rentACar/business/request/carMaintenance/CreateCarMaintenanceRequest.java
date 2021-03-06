package com.turkcell.rentACar.business.request.carMaintenance;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CreateCarMaintenanceRequest {

    @NotNull
    @Size(min = 2, max = 50)
    private String description;

    private int carId;
}
