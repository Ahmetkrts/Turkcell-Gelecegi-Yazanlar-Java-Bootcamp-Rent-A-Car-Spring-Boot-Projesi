package com.turkcell.rentACar.business.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentCarRequest {

    @NotNull
    private LocalDate dateOfIssue;

    private LocalDate dateOfReceipt;
    @NotNull
    private String rentFirstName;
    private String rentLastName;
    @NotNull
    private int carId;
    private List<Integer> AdditionalsIds;
    @NotNull
    private int fromCityId;
    @NotNull
    private int toCityId;


}
