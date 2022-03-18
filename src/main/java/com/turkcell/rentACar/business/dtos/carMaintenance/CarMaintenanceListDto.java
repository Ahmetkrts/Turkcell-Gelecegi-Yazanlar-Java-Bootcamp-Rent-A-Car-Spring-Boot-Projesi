package com.turkcell.rentACar.business.dtos.carMaintenance;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CarMaintenanceListDto {

    private int carMaintanenceId;
    private String description;
    private LocalDate returnDate;

    private String brandName;
}
