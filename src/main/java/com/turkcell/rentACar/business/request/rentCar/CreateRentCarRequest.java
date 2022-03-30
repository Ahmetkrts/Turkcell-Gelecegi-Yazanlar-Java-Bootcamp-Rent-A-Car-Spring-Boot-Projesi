package com.turkcell.rentACar.business.request.rentCar;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentCarRequest {

    @NotNull
    private LocalDate dateOfIssue;
    @NotNull
    private LocalDate dateOfReceipt;
    @NotNull
    private int customer_UserId;
    @NotNull
    private int carId;
    @NotNull
    private int fromCityId;
    @NotNull
    private int toCityId;


}
