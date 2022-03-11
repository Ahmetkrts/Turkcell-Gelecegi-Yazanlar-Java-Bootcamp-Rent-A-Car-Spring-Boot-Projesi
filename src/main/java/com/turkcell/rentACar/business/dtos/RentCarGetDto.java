package com.turkcell.rentACar.business.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentCarGetDto {


    private int rentCarId;
    private LocalDate dateOfIssue;
    private LocalDate dateOfReceipt;
    private String rentFirstName;
    private String rentLastName;
    private String brandName;
    private String description;
    private double totalFee;
    private String fromCityName;
    private String toCityName;

}
