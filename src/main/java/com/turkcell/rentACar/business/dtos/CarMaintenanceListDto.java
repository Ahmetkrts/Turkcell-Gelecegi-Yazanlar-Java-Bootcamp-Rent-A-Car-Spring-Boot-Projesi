package com.turkcell.rentACar.business.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CarMaintenanceListDto {

    private int id;
    private String description;
    private Date returnDate;

    private String brandName;
}
