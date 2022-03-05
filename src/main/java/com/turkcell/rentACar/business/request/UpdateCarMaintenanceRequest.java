package com.turkcell.rentACar.business.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UpdateCarMaintenanceRequest {

    private int id;
    @NotNull
    @Size(min = 2, max = 50)
    private String description;

    @PastOrPresent
    private Date returnDate;
    private int car_CarId;
}
