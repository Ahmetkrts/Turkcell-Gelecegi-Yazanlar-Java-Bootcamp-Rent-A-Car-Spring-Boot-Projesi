package com.turkcell.rentACar.business.request.carMaintenance;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UpdateCarMaintenanceRequest {

    private int carMaintanenceId;
    @NotNull
    @Size(min = 2, max = 50)
    private String description;

    @PastOrPresent
    private LocalDate returnDate;
    private int car_CarId;
}
